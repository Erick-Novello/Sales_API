package ericknovello.com.github.sales_api.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data
public class ClientDto {

    @NotEmpty(message = "Name field is mandatory")
    private String name;

    @CPF(message = "Insert a valid CPF")
    @NotEmpty(message = "Field name is mandatory")
    private String cpf;

}
