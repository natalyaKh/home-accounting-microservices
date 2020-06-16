package billsservice.dto;


import billsservice.enums.CategoryType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperationDto {
    @NotNull(message = "uuid of user may not be null")
    String userUuid;
    private String operationUuid;
    @NotNull(message = "uuid of bill may not be null")
    private String billUuid;
    @NotNull(message = "uuid of category may not be null")
    private String categoryUuid;
    private String subcategoryUuid;
    @NotNull(message = "sum may not be null")
    private Double sum;
    private String description;
    private LocalDate createDate;
    @NotNull(message = "currency may not be null")
    private String currency;
    @NotNull(message = "type may not be null")
    CategoryType type;
}
