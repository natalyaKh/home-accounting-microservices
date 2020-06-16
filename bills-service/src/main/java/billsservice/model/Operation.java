package billsservice.model;

import billsservice.enums.CategoryType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Operation extends BaseEntity {
    private String operationUuid;
    private String billUuid;
    private String category_uuid;
    private String subcategory_uuid;
    private Double sum;
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    private String description;
    private String userUuid;
    private String currency;
    private Date createDate;

}
