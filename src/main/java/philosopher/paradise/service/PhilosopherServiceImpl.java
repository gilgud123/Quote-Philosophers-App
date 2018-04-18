package philosopher.paradise.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import philosopher.paradise.dto.PhilosopherDTO;
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
        Set<Philosopher> philosopherSet = new HashSet<>();
        repo.findByCategories(categoryToMatch).iterator().forEachRemaining(philosopherSet::add);
        return philosopherSet;
    }

    public Philosopher addPhilosopher(Philosopher philosopher){
        return repo.save(philosopher);
    }

    public Philosopher editPhilosopher(Long id, String description){
        Philosopher toEdit = findById(id);
        toEdit.setDescription(description);
        return repo.save(toEdit);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }


}
