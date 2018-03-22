
/*
package philosopher.paradise.bootstrap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Philosopher;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.repository.PhilosopherRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class PhilosopherBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final PhilosopherRepository repo;

    public PhilosopherBootstrap(PhilosopherRepository repo) {
        this.repo = repo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        repo.saveAll(getPhilosophers());
        log.debug("Loading Bootstrap Data");
    }

    private List<Philosopher> getPhilosophers(){

        List<Philosopher> philosophers = new ArrayList<>();

        //creating quotes
        Quote q1 = new Quote();
        q1.setText("Plato is a friend, but truth is a better friend");

        //assigning quotes to philosophers
        Set<Quote> p1Set = new HashSet<>();
        p1Set.add(q1);

        //creating philosophers;
        Philosopher p1 = new Philosopher();
        p1.setName("Aristotle");
        p1.setCategory(Category.METAPHYSICS);
        p1.setQuotes(p1Set);
        p1.setDescription("The father of modern science methodology and ethics");

        return philosophers;
    }


}

*/
