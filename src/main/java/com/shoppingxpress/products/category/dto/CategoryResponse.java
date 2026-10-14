package com.shoppingxpress.products.category.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class CategoryResponse {
    private UUID id;
    private String name;
}
