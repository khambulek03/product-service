package com.shoppingxpress.products.product.service;

import com.shoppingxpress.products.category.domain.Category;
import com.shoppingxpress.products.category.repository.CategoryRepository;
import com.shoppingxpress.products.config.kafka.ProductEvent;
import com.shoppingxpress.products.config.kafka.ProductEventPublisher;
import com.shoppingxpress.products.exception.custom.CustomNotFoundException;
import com.shoppingxpress.products.product.domain.Product;
import com.shoppingxpress.products.product.dto.ProductRequest;
import com.shoppingxpress.products.product.dto.ProductResponse;
import com.shoppingxpress.products.product.repository.ProductRepository;
import com.shoppingxpress.products.utils.ApplicationResponseBuilder;
import com.shoppingxpress.products.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.shoppingxpress.products.config.kafka.EventType.PRODUCT_CREATED;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ApplicationResponseBuilder builder;

    private final ProductEventPublisher eventPublisher;

    private PageResponse<ProductResponse> buildPage(Page<ProductResponse> products) {
        return new PageResponse<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
    }

    private String sku(String name, String model) {
        if (name.isBlank() || model.isBlank()) {
            throw new IllegalArgumentException("Parameter name and model for product cannot be null/blank");
        }

        return format("P(%s)-M(%s)", name, model);
    }

    public ProductResponse addProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CustomNotFoundException("Could not find selected category"));

        Product product = Product.builder()
                .name(request.getName())
                .model(request.getModel())
                .sku(sku(request.getName(), request.getModel()))
                .category(category)
                .price(request.getPrice())
                .units(request.getUnits())
                .available(true)
                .supplier(request.getSupplier())
                .build();

        Product addedProduct = productRepository.save(product);

        eventPublisher.publish(new ProductEvent(
                randomUUID(),
                PRODUCT_CREATED,
                addedProduct.getName(),
                addedProduct.getSku(),
                addedProduct.getPrice(),
                addedProduct.getUnits(),
                addedProduct.getSupplier())
        );

        return builder.productResponse(addedProduct);
    }

    public PageResponse<ProductResponse> getProducts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("name").ascending());
        Page<ProductResponse> products = productRepository.findAllAvailableProducts(pageable, true)
                .map(builder::productResponse);
        return buildPage(products);
    }

    public PageResponse<ProductResponse> getProductsByName(String name, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("name").ascending());
        Page<ProductResponse> products = productRepository.findAllByName(pageable, name)
                .map(builder::productResponse);
        return buildPage(products);
    }

    public ProductResponse getProductsByModel(String model) {
        Product product = productRepository.findByModel(model)
                .orElseThrow(() -> new CustomNotFoundException(format("No product %s found", model)));
        return builder.productResponse(product);
    }

    public PageResponse<ProductResponse> getProductsByCategory(UUID categoryId, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("name"));
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        Page<ProductResponse> products = productRepository.findAllByCategory(pageable, category, true)
                .map(builder::productResponse);
        return buildPage(products);
    }

}