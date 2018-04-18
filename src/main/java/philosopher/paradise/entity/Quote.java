package philosopher.paradise.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Data
@Entity
public class Quote {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String text;

    @ManyToMany
    @JoinTable(name = "quote_topic",
            joinColumns = @JoinColumn(name = "QUOTE_ID"),
            inverseJoinColumns = @JoinColumn(name = "TOPIC_ID"))
    private Set<Topic> topics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PHILOSOPHER_ID")
    private Philosopher philosopher;

    public Quote() {
        this.id = new Random().nextLong();
    }

    public Quote(String text, Set<Topic> topics, Philosopher philosopher) {
        this();
        this.text = text;
        this.topics = topics;
        this.philosopher = philosopher;
    }
}
