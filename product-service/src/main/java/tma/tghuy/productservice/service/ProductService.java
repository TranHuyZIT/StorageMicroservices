package tma.tghuy.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tma.tghuy.productservice.dto.ProductRequest;
import tma.tghuy.productservice.dto.ProductResponse;
import tma.tghuy.productservice.model.Product;
import tma.tghuy.productservice.repo.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public Product createProduct(ProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product" + product.getId()  + "is saved");
        return product;
    }

    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream().map(this::mapToProductReponse).toList();
    }

    private ProductResponse mapToProductReponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .name(product.getName())
                .build();
    }
}
