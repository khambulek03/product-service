package com.shoppingxpress.products.product.controller;

import com.shoppingxpress.products.product.dto.ProductRequest;
import com.shoppingxpress.products.product.dto.ProductResponse;
import com.shoppingxpress.products.product.service.ProductService;
import com.shoppingxpress.products.utils.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/new")
    public ResponseEntity<ProductResponse> add(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.addProduct(request);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(format("/new-product?productId=%s", response.getId())).toUriString());
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        PageResponse<ProductResponse> response = productService.getProducts(page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByName(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit,
            @RequestParam("name") String name
    ) {
        PageResponse<ProductResponse> response = productService.getProductsByName(name, page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/model")
    public ResponseEntity<ProductResponse> getProductsByModel(
            @RequestParam("model") String model
    ) {
        ProductResponse response = productService.getProductsByModel(model);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    public ResponseEntity<PageResponse<ProductResponse>> getProductsByCategory(
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageResponse<ProductResponse> response = productService.getProductsByCategory(categoryId, page, limit);
        return ResponseEntity.ok(response);
    }

}
