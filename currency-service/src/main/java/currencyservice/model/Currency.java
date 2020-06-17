package currencyservice.model;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String currencyName;
    @Column(nullable = false)
    private String userUuid;
    @Column(nullable = false)
    private String abbr;
    private Boolean deleted;

}
