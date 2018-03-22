package philosopher.paradise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import philosopher.paradise.entity.Topic;
import philosopher.paradise.repository.TopicRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicServiceImpl implements TopicService{

    private TopicRepository repo;

    @Autowired
    public TopicServiceImpl(TopicRepository repo) {
        this.repo = repo;
    }

    @Override
    public Set<Topic> getTopics() {
        Set<Topic> topics = new HashSet<>();
        repo.findAll().iterator().forEachRemaining(topics::add);
        return topics;
    }

    @Override
    public Topic getTopicById(Long id) {
        Optional<Topic> optionalTopic = repo.findById(id);

        if(!optionalTopic.isPresent()){
            throw new RuntimeException("Topic Not Found!");
        }
        return optionalTopic.get();
    }
}
