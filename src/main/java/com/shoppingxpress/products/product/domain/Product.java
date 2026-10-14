package com.shoppingxpress.products.product.domain;

import com.shoppingxpress.products.auditing.EntityAuditing;
import com.shoppingxpress.products.category.domain.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Product extends EntityAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, updatable = false)
    private Category category;

    @Column(name = "stock_keeping_unit", unique = true, nullable = false, updatable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String model;

    @DecimalMin("0.0")
    @Column(precision = 7, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @Column(nullable = false)
    private Long units = 0L;

    @Builder.Default
    @Column(nullable = false)
    private boolean available = false;
}
