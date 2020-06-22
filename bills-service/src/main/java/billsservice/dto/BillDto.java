package billsservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDto {

    String billUuid;
    @NotNull(message = "bill_name may not be null")
    String billName;
    Integer billNumber;
    Double startSum;
    @NotNull(message = "user_uuid may not be null")
    String userUuid;
    String description;
    Date createDate;
}
