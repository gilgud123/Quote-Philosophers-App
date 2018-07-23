package philosopher.paradise.entity;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes="The database generated philosopher ID")
    private Long id;

    @ApiModelProperty(notes="The philosopher's name")
    private String name;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "categories",
            joinColumns = @JoinColumn(name = "philosopher_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "category_id")
    @ApiModelProperty(notes="A set of categories covering the philosopher's work.")
    private Set<Category> categories = new HashSet<>();

    @Lob
    @ApiModelProperty(notes="A description of philospher's life and work.")
    private String description;

    @OneToMany
    @ApiModelProperty(notes="A set of all the philosopher's quotes in the database.")
    private Set<Quote> quotes;

    public Philosopher() {}

    public Philosopher(String name, Set<Category> categories, String description, Set<Quote> quotes) {
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.quotes = quotes;
    }
}
