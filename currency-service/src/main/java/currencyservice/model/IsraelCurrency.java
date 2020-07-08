package currencyservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class IsraelCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String country;
    private String abbr;
    /**
     * how change course from yesterday
     */
    private Double changeCourse;
    /**
     * course for changing
     */
    private Double rate;
    private Double quantity;



}