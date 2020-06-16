package billsservice.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillNumbersDto {
    String billUuid;
    Integer billNumber;
    long id;
    String userUuid;
    Date createDate;
    Boolean deleted;
    String billName;
    Double startSum;
    String description;
}
