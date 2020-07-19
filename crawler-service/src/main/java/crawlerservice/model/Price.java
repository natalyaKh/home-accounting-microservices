package crawlerservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Price {
    private Integer rub;
    private Integer cop;
    private String val;
}
