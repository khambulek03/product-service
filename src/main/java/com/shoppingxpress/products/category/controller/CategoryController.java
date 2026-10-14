package com.shoppingxpress.products.category.controller;

import com.shoppingxpress.products.category.dto.CategoryResponse;
import com.shoppingxpress.products.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> categories() {
        List<CategoryResponse> response = categoryService.categories();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/new")
    public ResponseEntity<CategoryResponse> add(@RequestParam(name = "name") String name) {
        CategoryResponse response = categoryService.addCategory(name);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(format("/categories/category?name=%s", response.getName()))
                .toUriString());
        return ResponseEntity.created(uri).body(response);
    }
}
