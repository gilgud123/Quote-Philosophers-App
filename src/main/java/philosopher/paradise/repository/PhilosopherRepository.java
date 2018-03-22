package philosopher.paradise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Philosopher;
import java.util.List;
import java.util.Set;

public interface PhilosopherRepository extends CrudRepository<Philosopher, Long> {

    Set<Philosopher> findAllByOrderByNameAsc();

    Set<Philosopher> findByCategories(Category category);

    Set<Philosopher> findByName(String name);
}
