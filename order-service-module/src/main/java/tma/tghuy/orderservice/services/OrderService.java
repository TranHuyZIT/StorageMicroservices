package tma.tghuy.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tma.tghuy.orderservice.dto.OrderLineItemsDTO;
import tma.tghuy.orderservice.dto.OrderRequestDTO;
import tma.tghuy.orderservice.model.Order;
import tma.tghuy.orderservice.model.OrderLineItems;
import tma.tghuy.orderservice.repos.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequestDTO requestDTO){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = requestDTO.getOrderLineItemsDTOS()
                .stream()
                .map(this::mapToModel)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);
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
