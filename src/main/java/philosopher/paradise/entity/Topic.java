package philosopher.paradise.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
public class Topic {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @ManyToMany
    private Set<Quote> quotes;
}
