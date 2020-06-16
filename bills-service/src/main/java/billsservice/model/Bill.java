package billsservice.model;

import lombok.*;

import java.util.Date;
import javax.persistence.*;

@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Bill extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userUuid;
    private Date createDate;
    private Boolean deleted;
    private String billUuid;
    private String billName;
    private Integer billNumber;
    private Double startSum;
    private String description;


}
