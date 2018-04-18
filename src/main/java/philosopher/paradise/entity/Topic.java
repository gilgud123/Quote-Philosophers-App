package philosopher.paradise.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Entity
public class Topic {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToMany
    private Set<Quote> quotes;

    private Topic(){
        this.id = new Random().nextLong();
    }

    public Topic(String text, Set<Quote> quotes) {
        this();
        this.text = text;
        this.quotes = quotes;
    }
}
