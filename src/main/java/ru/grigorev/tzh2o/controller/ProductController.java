package ru.grigorev.tzh2o.controller;

import ru.grigorev.tzh2o.dto.ProductDto;
import ru.grigorev.tzh2o.enums.ProductType;
import ru.grigorev.tzh2o.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.addProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductsByType(@RequestParam ProductType type) {
        return ResponseEntity.ok(productService.getProductsByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body("Ошибка: неверный формат поля '" + ex.getName() +
                "'. Ожидается тип: " + ex.getRequiredType().getSimpleName());
    }
}