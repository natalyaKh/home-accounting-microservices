package billsservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillAllDto {
    String billUuid;
    String billName;
    String userUuid;
    List<OperationsByDateDto> operations;
    Double sumByBill;
    LocalDate createDate;
}
