package tma.tghuy.orderservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tma.tghuy.orderservice.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
