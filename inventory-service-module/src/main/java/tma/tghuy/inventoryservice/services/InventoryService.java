package tma.tghuy.inventoryservice.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tma.tghuy.inventoryservice.dtos.InventoryResponse;
import tma.tghuy.inventoryservice.models.Inventory;
import tma.tghuy.inventoryservice.repos.InventoryRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> getSkuCodeStatus(List<String> skuCodes){
        log.info("Checking inventory...");
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(
                inventory ->
                     InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0)
                            .build()

        ).toList();
    }

}
