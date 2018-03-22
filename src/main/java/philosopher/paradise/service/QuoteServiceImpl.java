package philosopher.paradise.service;

import org.springframework.stereotype.Service;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;
import philosopher.paradise.repository.QuoteRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private QuoteRepository repo;

    public QuoteServiceImpl(QuoteRepository repo) {
        this.repo = repo;
    }

    public Set<Quote> getQuotes(){
        Set<Quote> quotes = new HashSet<>();
        repo.findAll().iterator().forEachRemaining(quotes::add);
        return quotes;
    }

    public Set<Quote> getQuotesByTopic(Topic topic){
        Set<Quote> quotes = new HashSet<>();
        repo.findByTopics(topic).iterator().forEachRemaining(quotes::add);
        return quotes;
    }

    @Override
    public Set<Quote> getQuotesByPhilosopherId(Long id) {
        Set<Quote> quotes = new HashSet<>();
        repo.findByPhilosopherId(id).iterator().forEachRemaining(quotes::add);
        return quotes;
    }

    public Quote getRandomQuote(){
        List<Quote> quotes = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(quotes::add);
        Random rand = new Random();
        int id = rand.nextInt(quotes.size());
        return quotes.get(id);
    }
}
