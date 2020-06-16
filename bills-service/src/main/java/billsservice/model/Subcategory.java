package billsservice.model;

import billsservice.enums.CategoryType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Subcategory extends  BaseEntity{
    private String categoryUuid;
    private String subcategoryUuid;
    private String subcategoryName;
    @Enumerated(EnumType.STRING)
    private CategoryType type;
    private String userUuid;
    private String description;
}
