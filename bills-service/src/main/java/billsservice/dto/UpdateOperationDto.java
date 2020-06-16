package billsservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateOperationDto {
    @NotNull(message = "uuid of bill may not be null")
    private String billUuid;
    @NotNull(message = "sum of operation may not be null")
    private Double sum;
    private String description;
    private LocalDate createDate;
    @NotNull(message = "currency of operation may not be null")
    private String currency;
}
