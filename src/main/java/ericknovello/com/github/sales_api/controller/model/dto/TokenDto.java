package ericknovello.com.github.sales_api.controller.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {

    private String login;
    private String password;

}
