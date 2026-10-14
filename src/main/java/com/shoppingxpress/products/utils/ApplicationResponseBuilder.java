package com.shoppingxpress.products.utils;

import com.shoppingxpress.products.category.domain.Category;
import com.shoppingxpress.products.category.dto.CategoryResponse;
import com.shoppingxpress.products.product.domain.Product;
import com.shoppingxpress.products.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ApplicationResponseBuilder {

    public CategoryResponse categoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public ProductResponse productResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .category(categoryResponse(product.getCategory()))
                .stockKeepingUnit(product.getSku())
                .name(product.getName())
                .model(product.getModel())
                .price(product.getPrice())
                .isAvailable(product.isAvailable())
                .build();
    }
}
