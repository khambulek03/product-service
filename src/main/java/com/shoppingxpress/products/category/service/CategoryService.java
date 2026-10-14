package com.shoppingxpress.products.category.service;

import com.shoppingxpress.products.category.domain.Category;
import com.shoppingxpress.products.category.dto.CategoryResponse;
import com.shoppingxpress.products.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> categories(){
        return categoryRepository.findAll()
                .stream()
                .map(category ->
                        CategoryResponse.builder().id(category.getId()).name(category.getName()).build())
                .toList();
    }

    public CategoryResponse addCategory(String name) {
        Category category = Category.builder().name(name).build();
        Category addedCategory = categoryRepository.save(category);
        return new CategoryResponse(addedCategory.getId(), addedCategory.getName());
    }
}
