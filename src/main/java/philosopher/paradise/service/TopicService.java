package philosopher.paradise.service;

import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;

import java.util.Optional;
import java.util.Set;

public interface TopicService {

    Set<Topic> getTopics();

    Topic getTopicById(Long id);


}
