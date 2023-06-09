package tma.tghuy.inventoryservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tma.tghuy.inventoryservice.dtos.InventoryResponse;
import tma.tghuy.inventoryservice.services.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    public List<InventoryResponse> getInventories(@RequestParam List<String>skuCode){
        return inventoryService.getSkuCodeStatus(skuCode);
    }
}
