package billsservice.dto;


import billsservice.enums.CategoryType;
import lombok.*;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubCategoryDto {
    @NotNull(message = "subcategory name is necessary")
    String subCategoryName;
    String subCategoryUuid;
    @NotNull(message = "user_uuid is necessary")
    String userUuid;
    CategoryDto category;
    CategoryType type;
    String decryption;
}
