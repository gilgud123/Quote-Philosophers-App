package philosopher.paradise.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import philosopher.paradise.dto.QuoteDTO;
import philosopher.paradise.entity.Philosopher;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;
import philosopher.paradise.repository.PhilosopherRepository;
import philosopher.paradise.repository.QuoteRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private QuoteRepository repo;
    private PhilosopherRepository pRepo;

    public QuoteServiceImpl(QuoteRepository repo, PhilosopherRepository pRepo) {
        this.repo = repo;
        this.pRepo = pRepo;
    }

    public Set<Quote> getQuotes(){
        Set<Quote> quotes = new HashSet<>();
        repo.findAll().iterator().forEachRemaining(quotes::add);
        return quotes;
    }

    @Override
    public Quote getQuoteById(Long id) {

        if(!repo.findById(id).isPresent()) {
            throw new RuntimeException("Quote not found!");
        }
        return repo.findById(id).get();
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

    @Override
    @Transactional
    public QuoteDTO createQuote(QuoteDTO dto) {
        Quote quote = new Quote();

        Optional<Philosopher> p = pRepo.findByName(dto.getPhilosopherName());
        if(!p.isPresent()){
            throw  new RuntimeException("Philosopher does not exist in our database!");
        }

        quote.setText(dto.getText());
        quote.setPhilosopher(p.get());
        quote.setTopics(dto.getTopics());
        repo.save(quote);

        return dto;
    }

    /*@Override
    public Quote createQuote(String text, Set<String> keywords, Long id) {
        Set<Topic> topics = new HashSet<>();
        keywords.iterator().forEachRemaining(k -> topics.add(new Topic(k)));
        Optional<Philosopher> p = pRepo.findById(id);
        if(!p.isPresent()){throw new RuntimeException("Philosopher does not exist!");}
        Quote quote = new Quote(text, topics, p.get());
        repo.save(quote);
        return quote;
    }*/

    @Override
    public Quote updateQuote(Long id, String text) {
        Quote quoteToUpdate = getQuoteById(id);
        quoteToUpdate.setText(text);
        repo.save(quoteToUpdate);
        return quoteToUpdate;
    }

    @Override
    public Quote getRandomQuote(){
        List<Quote> quotes = new ArrayList<>();
        repo.findAll().iterator().forEachRemaining(quotes::add);
        Random rand = new Random();
        int id = rand.nextInt(quotes.size());
        return quotes.get(id);
    }

    @Override
    public void deleteQuote(Long id) {
        repo.deleteById(id);
    }
}
