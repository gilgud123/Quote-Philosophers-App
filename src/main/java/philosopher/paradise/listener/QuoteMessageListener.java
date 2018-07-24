package philosopher.paradise.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.repository.QuoteRepository;

import java.util.Map;
import java.util.logging.LogManager;

@Component
public class QuoteMessageListener {

    private QuoteRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(QuoteMessageListener.class);

    public QuoteMessageListener(QuoteRepository repo) {
        this.repo = repo;
    }

    public void receiveMessage(Map<String, String> message){
        logger.info("Received <" + message + ">");
        Long id = Long.valueOf(message.get("id"));
        Quote quote = repo.findById(id).orElse(null);
        if (quote != null) {
            quote.setMessageReceived(true);
            quote.setMessageCount(quote.getMessageCount() + 1);
            repo.save(quote);
        }
        logger.info("Message processed...");
    }
}

