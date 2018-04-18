package philosopher.paradise.service;

import philosopher.paradise.dto.QuoteDTO;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;

import java.util.List;
import java.util.Set;

public interface QuoteService {

    Set<Quote> getQuotes();

    Quote getQuoteById(Long id);

    Set<Quote> getQuotesByTopic(Topic topic);

    Set<Quote> getQuotesByPhilosopherId(Long id);

    Quote getRandomQuote();

    QuoteDTO createQuote(QuoteDTO dto);

    Quote updateQuote(Long is, String text);

    void deleteQuote(Long id);
}
