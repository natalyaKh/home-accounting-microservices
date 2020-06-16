package billsservice.dto;

import billsservice.enums.CategoryType;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillAllDetailsDto {
    String billUuid;
    String billName;
    String userUuid;
    String operationName;
    Double operationSum;
    CategoryType operationType;
    Double sumByBill;
    LocalDate createdDate;
}
