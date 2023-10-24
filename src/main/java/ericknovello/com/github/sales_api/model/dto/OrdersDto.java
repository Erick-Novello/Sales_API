package ericknovello.com.github.sales_api.model.dto;

import ericknovello.com.github.sales_api.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto {

    @NotNull(message = "Insert client code")
    private Integer client;

    @NotNull(message = "Field total is mandatory")
    private BigDecimal total;

    @NotEmptyList(message = "Order cannot be placed without item")
    private List<ItemsOrdersDto> items;

}
