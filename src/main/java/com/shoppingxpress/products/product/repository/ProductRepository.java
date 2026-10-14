package com.shoppingxpress.products.product.repository;

import com.shoppingxpress.products.category.domain.Category;
import com.shoppingxpress.products.product.domain.Product;
import com.shoppingxpress.products.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p.available = :available ORDER BY p.name")
    Page<Product> findAllAvailableProducts(Pageable pageable, @Param("available") boolean available);

    Page<Product> findAllByName(Pageable pageable, String name);

    Page<Product> findAllByModel(Pageable pageable, String name);

    Optional<Product> findByModel(String model);

    List<Product> category(Category category);

    @Query("SELECT p FROM Product p WHERE p.available = :availability AND p.category = :category")
    Page<Product> findAllByCategory(Pageable pageable, Category category, boolean availability);
}
