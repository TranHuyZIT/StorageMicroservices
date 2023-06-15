package tma.tghuy.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tma.tghuy.inventoryservice.dtos.InventoryResponse;
import tma.tghuy.orderservice.dto.OrderLineItemsDTO;
import tma.tghuy.orderservice.dto.OrderRequestDTO;
import tma.tghuy.orderservice.model.Order;
import tma.tghuy.orderservice.model.OrderLineItems;
import tma.tghuy.orderservice.repos.OrderRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public Map<String, String> placeOrder(OrderRequestDTO requestDTO){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = requestDTO.getOrderLineItemsDTOS()
                .stream()
                .map(this::mapToModel)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodes = orderLineItemsList.stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class).block();
        if (inventoryResponses == null || inventoryResponses.length == 0) {
            throw new IllegalArgumentException("Inventory is empty");
        }
        Boolean isInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if (isInStock){
            orderRepository.save(order);
            Map<String, String> message = new HashMap<>();
            message.put("message", "Placed order successfully");
            return message;
        }
        throw new IllegalArgumentException("Product is not in stock, please try again later.");


    }

    private OrderLineItems mapToModel(OrderLineItemsDTO orderLineItemsDTO){
        return OrderLineItems.builder()
                .id(orderLineItemsDTO.getId())
                .price(orderLineItemsDTO.getPrice())
                .quantity(orderLineItemsDTO.getQuantity())
                .skuCode(orderLineItemsDTO.getSkuCode())
                .build();
    }
}
