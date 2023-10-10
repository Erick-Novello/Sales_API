package ericknovello.com.github.sales_api.controller;

import ericknovello.com.github.sales_api.model.OrderedItem;
import ericknovello.com.github.sales_api.model.Orders;
import ericknovello.com.github.sales_api.model.dto.InformationItemOrderDto;
import ericknovello.com.github.sales_api.model.dto.InformationOrderDto;
import ericknovello.com.github.sales_api.model.dto.OrdersDto;
import ericknovello.com.github.sales_api.model.dto.UpdateStatusOrderDto;
import ericknovello.com.github.sales_api.model.enums.StatusOrder;
import ericknovello.com.github.sales_api.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "Orders API")
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;


    @PostMapping
    @ApiOperation(value = "Insert a new Order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order with successfully"),
            @ApiResponse(code = 400, message = "Error Validation"),
            @ApiResponse(code = 403, message = "User without access")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Integer includeOrder(@RequestBody @Valid OrdersDto ordersDto) {
        return ordersService.includeOrder(ordersDto).getId();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Show data about new Order")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Order found successfully"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @ResponseStatus(HttpStatus.OK)
    public InformationOrderDto showOrder(@PathVariable Integer id){
        return ordersService.showOrder(id)
                .map(this::builderInformacaoPedidoDto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Order not found"));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing order ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Order found successfully"),
            @ApiResponse(code = 400, message = "Order not found"),
            @ApiResponse(code = 403, message = "User without access right"),
            @ApiResponse(code = 405, message = "Invalid Status Option")
    })
    @ResponseStatus(HttpStatus.OK)
    private void updateStatus(@PathVariable Integer id, @RequestBody UpdateStatusOrderDto updateStatusOrderDto) {
        ordersService.updateStatus(id, StatusOrder.valueOf(updateStatusOrderDto.getNewStatus()));
    }

    private InformationOrderDto builderInformacaoPedidoDto(Orders orders) {
        String dataPedido = orders.getDateOrder().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return InformationOrderDto.builder()
                .code(orders.getId())
                .orderDate(dataPedido)
                .cpf(orders.getClient().getCpf())
                .clientName(orders.getClient().getName())
                .total(orders.getTotal())
                .status(orders.getStatusOrder().name())
                .items(builderInformacaoItemPedidoDto(orders.getItems()))
                .build();
    }

    private List<InformationItemOrderDto> builderInformacaoItemPedidoDto (List<OrderedItem> itens){
        return itens.stream()
                .map(item -> InformationItemOrderDto.builder()
                        .productDescription(item.getProduct().getDescription())
                        .unitPrice(item.getProduct().getPrice())
                        .amount(item.getAmount())
                        .build()
                ).collect(Collectors.toList());
    }

}