package tma.tghuy.orderservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tma.tghuy.orderservice.dto.OrderRequestDTO;
import tma.tghuy.orderservice.services.OrderService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        orderService.placeOrder(orderRequestDTO);
        Map<String, String> message = new HashMap<>();
        message.put("message", "Placed order successfully");
        return message;
    }
}
