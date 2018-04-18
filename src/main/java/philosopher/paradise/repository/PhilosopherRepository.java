package philosopher.paradise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import philosopher.paradise.entity.Category;
import philosopher.paradise.entity.Philosopher;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PhilosopherRepository extends CrudRepository<Philosopher, Long> {

    Set<Philosopher> findAllByOrderByNameAsc();

    Set<Philosopher> findByCategories(Category category);

    Optional<Philosopher> findById(Long id);

    Optional<Philosopher> findByName(String name);

    void deleteById(Long id);
}
