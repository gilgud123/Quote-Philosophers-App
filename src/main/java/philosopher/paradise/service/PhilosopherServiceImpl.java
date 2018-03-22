package philosopher.paradise.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Philosopher;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.repository.PhilosopherRepository;

import java.io.FileNotFoundException;
import java.util.*;

@Service
public class PhilosopherServiceImpl implements PhilosopherService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PhilosopherServiceImpl.class);
    private final PhilosopherRepository repo;

    @Autowired
    public PhilosopherServiceImpl(PhilosopherRepository repo) {
       this.repo = repo;
    }

    @Override
    public List<Philosopher> getPhilosophers(){
        log.debug("I'm in the Philosopher service getPhilosophers method!");
        List<Philosopher> philosopherList = new ArrayList<>();
        repo.findAllByOrderByNameAsc().iterator().forEachRemaining(philosopherList::add);
        return philosopherList;
    }

    @Override
    public Philosopher findById(Long id) {

        Optional<Philosopher> philosopherOptional = repo.findById(id);

        if (!philosopherOptional.isPresent()) {
            throw new RuntimeException("Philosopher Not Found!");
        }

        return philosopherOptional.get();
    }

    @Override
    public Set<Philosopher> getPhilosopherByCategory(String category){
        Category categoryToMatch = Category.valueOf(category);
        Set<Philosopher> philosopherList = new HashSet<>();
        repo.findByCategories(categoryToMatch).iterator().forEachRemaining(philosopherList::add);
        return philosopherList;
    }
}
