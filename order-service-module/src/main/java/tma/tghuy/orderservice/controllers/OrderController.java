package tma.tghuy.orderservice.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tma.tghuy.orderservice.dto.OrderRequestDTO;
import tma.tghuy.orderservice.services.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name="inventory")
    @Retry(name = "inventory")
    public CompletableFuture<Map<String, String>> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws InterruptedException {
        log.info("Order received...");
        Thread.sleep(1500);
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequestDTO));
    }
    public CompletableFuture<Map<String, String>> fallBackMethod(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "Oops, something went wrong, please order after some time");
        return CompletableFuture.supplyAsync(() -> errorMap);
    }

}
