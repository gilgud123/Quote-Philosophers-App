package philosopher.paradise.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Data
@Entity
public class Philosopher {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "categories",
            joinColumns = @JoinColumn(name = "philosopher_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    private Set<Category> categories;

    @Lob
    private String description;

    @OneToMany
    private Set<Quote> quotes = new HashSet<>();

    public Philosopher() {}

    public Philosopher(String name, Set<Category> categories, String description, Set<Quote> quotes) {
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.quotes = quotes;
    }
}
