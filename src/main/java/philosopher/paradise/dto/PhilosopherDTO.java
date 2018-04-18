package philosopher.paradise.dto;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;
import philosopher.paradise.entity.Category;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

@Getter
@Setter
public class PhilosopherDTO {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    //private Set<Category> categories;

    @NonNull
    private String description;

    public PhilosopherDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
