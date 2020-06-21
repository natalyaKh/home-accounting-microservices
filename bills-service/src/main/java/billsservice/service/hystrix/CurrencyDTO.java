package billsservice.service.hystrix;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyDTO {
    private String currencyName;
    private String abbr;
}
