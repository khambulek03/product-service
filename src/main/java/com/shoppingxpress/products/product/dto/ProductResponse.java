package com.shoppingxpress.products.product.dto;

import com.shoppingxpress.products.category.dto.CategoryResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class ProductResponse {

    private UUID id;

    private CategoryResponse category;

    private String stockKeepingUnit;

    private String name;

    private String model;

    private BigDecimal price;

    private boolean isAvailable;
}
