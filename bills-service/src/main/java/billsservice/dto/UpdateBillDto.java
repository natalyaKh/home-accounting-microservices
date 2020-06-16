package billsservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateBillDto {

    String billName;
    Integer billNumber;
    Double startSum;
    String description;
}
