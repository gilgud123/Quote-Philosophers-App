package philosopher.paradise.repository;

import org.springframework.data.repository.CrudRepository;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;

import java.util.Optional;
import java.util.Set;

public interface TopicRepository extends CrudRepository<Topic, Long>{

    Optional<Topic> findById(Long id);
}
