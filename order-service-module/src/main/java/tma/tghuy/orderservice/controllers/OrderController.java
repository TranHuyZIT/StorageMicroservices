package tma.tghuy.orderservice.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.spring6.fallback.FallbackMethod;
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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    public Map<String, String> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        orderService.placeOrder(orderRequestDTO);
        Map<String, String> message = new HashMap<>();
        message.put("message", "Placed order successfully");
        return message;
    }
    public Map<String, String> fallBackMethod(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "Oops, something went wrong, please order after some time");
        return errorMap;
    }
}
