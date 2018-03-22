package philosopher.paradise.service;

import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;

import java.util.List;
import java.util.Set;

public interface QuoteService {

    Set<Quote> getQuotes();

    Set<Quote> getQuotesByTopic(Topic topic);

    Set<Quote> getQuotesByPhilosopherId(Long id);
}
