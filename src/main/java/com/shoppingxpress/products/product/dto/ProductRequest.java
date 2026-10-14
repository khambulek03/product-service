package com.shoppingxpress.products.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class ProductRequest {

    private UUID categoryId;

    private String name;

    private String model;

    private BigDecimal price;

    private Long units;

    private String supplier;
}
