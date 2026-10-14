package com.shoppingxpress.products.utils;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class PageResponse<T> {

    private List<T> content;

    private int number;

    private int size;

    private Long totalElements;

    private int totalPages;
}
