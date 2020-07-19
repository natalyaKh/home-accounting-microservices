package crawlerservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private String id;
    private String title;
    private String category;
    private String subcategory;
    private Integer rub;
    private Integer cop;

}
