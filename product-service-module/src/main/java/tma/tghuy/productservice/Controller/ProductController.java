package tma.tghuy.productservice.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tma.tghuy.productservice.dto.ProductRequest;
import tma.tghuy.productservice.dto.ProductResponse;
import tma.tghuy.productservice.model.Product;
import tma.tghuy.productservice.repo.ProductRepository;
import tma.tghuy.productservice.service.ProductService;
import java.util.List;
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProduct();
    }
}
