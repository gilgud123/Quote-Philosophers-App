package philosopher.paradise.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import philosopher.paradise.entity.Quote;
import philosopher.paradise.entity.Topic;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class QuoteDTO {

    private Long id;
    private String text;
    private Set<Topic> topics;
    private String philosopherName;
}
