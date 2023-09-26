package ericknovello.com.github.sales_api.model.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsOrdersDto {

    private Integer product;
    private Integer amount;

}
