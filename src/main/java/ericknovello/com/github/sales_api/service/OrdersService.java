package ericknovello.com.github.sales_api.service;

import ericknovello.com.github.sales_api.exception.BusinessRulesException;
import ericknovello.com.github.sales_api.exception.OrderNotFoundException;
import ericknovello.com.github.sales_api.model.Client;
import ericknovello.com.github.sales_api.model.OrderedItem;
import ericknovello.com.github.sales_api.model.Orders;
import ericknovello.com.github.sales_api.model.Product;
import ericknovello.com.github.sales_api.model.dto.OrdersDto;
import ericknovello.com.github.sales_api.model.enums.StatusOrder;
import ericknovello.com.github.sales_api.repository.ClientRepository;
import ericknovello.com.github.sales_api.repository.OrderedItemRepository;
import ericknovello.com.github.sales_api.repository.OrdersRepository;
import ericknovello.com.github.sales_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    private final OrderedItemRepository orderedItemRepository;

    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;

    public Orders includeOrder(OrdersDto ordersDto) {
        validItems(ordersDto);
        Client client = findClient(ordersDto);
        Orders pedido = buildOrder(ordersDto, client);

        List<OrderedItem> itens = builderItemOrder(ordersDto, pedido);
        ordersRepository.save(pedido);
        orderedItemRepository.saveAll(itens);

        return pedido;
    }

    public Optional<Orders> showOrder(Integer id) {
        return ordersRepository.findByIdFetchItems(id);
    }

    @Transactional
    public void updateStatus(Integer id, StatusOrder statusOrder) {
        ordersRepository.findById(id)
                .map(orders -> {
                    orders.setStatusOrder(statusOrder);
                    return ordersRepository.save(orders);
                }).orElseThrow(() -> new OrderNotFoundException("Order not found."));
    }

    private List<OrderedItem> builderItemOrder(OrdersDto ordersDto, Orders orders) {
        return ordersDto.getItems().stream()
                .map(itemsOrdersDto -> {
                    Product product = productRepository.findById(itemsOrdersDto.getProduct())
                            .orElseThrow(() -> new BusinessRulesException("Código de Cliente inválido"));

                    return OrderedItem.builder()
                            .orders(orders)
                            .product(product)
                            .amount(itemsOrdersDto.getAmount())
                            .build();
                }).collect(Collectors.toList());
    }

    private void validItems(OrdersDto ordersDto) {
        if (ordersDto.getItems().isEmpty()) {
            throw new BusinessRulesException("It is no possible to place an order without items");
        }
    }

    public Orders buildOrder(OrdersDto pedidoDto, Client client) {
        return Orders.builder()
                .client(client)
                .dateOrder(LocalDate.now())
                .total(pedidoDto.getTotal())
                .statusOrder(StatusOrder.PENDING)
                .build();
    }

    private Client findClient(OrdersDto ordersDto) {
        return clientRepository.findById(ordersDto.getClient())
                .orElseThrow(() -> new BusinessRulesException("Client code invalid"));
    }

}