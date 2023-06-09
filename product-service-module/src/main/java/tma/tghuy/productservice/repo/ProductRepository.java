package tma.tghuy.productservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tma.tghuy.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
