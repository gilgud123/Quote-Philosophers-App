package philosopher.paradise.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import philosopher.paradise.config.MessagingConfig;
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

    private static final Logger logger = LoggerFactory.getLogger(QuoteServiceImpl.class);

    private QuoteRepository repo;
    private PhilosopherRepository pRepo;
    private RabbitTemplate rabbitTemplate;

    public QuoteServiceImpl(QuoteRepository repo, PhilosopherRepository pRepo, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.pRepo = pRepo;
        this.rabbitTemplate = rabbitTemplate;
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
    public Quote createQuote(Quote quote) {
         return repo.save(quote);
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

    @Override
    public void sendQuoteMessage(String id){
        Map<String, String> messagemap = new HashMap<>();
        messagemap.put("id", id);
        logger.info("Sending the index request through queue message");
        rabbitTemplate.convertAndSend(MessagingConfig.QUEUE_NAME, messagemap);
    }
}