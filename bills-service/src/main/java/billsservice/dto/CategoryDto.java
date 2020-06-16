package billsservice.dto;


import billsservice.enums.CategoryType;
import lombok.*;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    @NotNull(message = "name of category may not be null")
    String categoryName;
    String categoryUuid;
    @NotNull(message = "user_uuid is necessary")
    String userUuid;
    @NotNull(message = "type may not be null")
    CategoryType type;
    String decryption;
}
