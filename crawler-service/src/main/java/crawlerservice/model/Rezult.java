package crawlerservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Rezult {
    String productName;
    Price price;
}
