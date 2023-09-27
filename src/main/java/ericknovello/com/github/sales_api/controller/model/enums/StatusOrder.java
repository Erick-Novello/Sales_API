package ericknovello.com.github.sales_api.controller.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StatusOrder {

    PENDING(0, "Pending"),
    ACCOMPLISHED(1, "Accomplished"),
    CANCELED(2, "Canceled"),
    FINISHED(3, "Finished");

    private int cod;
    private String description;

    public static StatusOrder toEnum(Integer cod) {
        return Arrays.stream(StatusOrder.values())
                .filter(x -> cod != null && cod.equals(x.getCod()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID " + cod));
    }
}
