package tma.tghuy.inventoryservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_inventory")
@Data
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity;
}
