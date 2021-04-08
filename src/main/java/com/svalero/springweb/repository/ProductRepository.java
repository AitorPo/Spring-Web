package com.svalero.springweb.repository;

import com.svalero.springweb.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Set<Product> findAll();
    Set<Product> findByCategory(String category);
    Set<Product> findByName(String name);
    Set<Product> findByPrice(float price);
    Set<Product> findByOnSale(boolean onSale);
    Optional<Product> findById(long id);
    Set<Product> findByCategoryAndNameAndPrice(String category, String name, float price);
    Set<Product> findByCategoryAndName(String category, String name);
    Set<Product> findByCategoryAndPrice(String category, float price);
    Set<Product> findByNameAndPrice(String name, float price);

    @Query("SELECT DISTINCT category FROM products WHERE category LIKE :category")
    Optional<String> findCategoryName(@Param("category") String category);

    @Query("SELECT SUM(price) FROM products WHERE category LIKE :category")
    Double sumAllProductsByCategory(@Param("category") String category);

    @Query("SELECT SUM(price) FROM products")
    Double sumAllProducts();

    @Query("FROM products WHERE name = :name")
    Product getProductByName(@Param("name") String name);

    @Query("FROM products WHERE id = :id")
    Product getProduct(@Param("id") long id);

    // Rescatamos toda la información de los productos cuyo nombre contenga el String que pasamos por parámetro
    @Query("FROM products WHERE name LIKE %:name%")
    Set<Product> getProducts(@Param("name") String name);

    // Rescatamos toda la información de los productos cuyo nombre contenga el String que pasamos por parámetro y la categoría "literal"
    @Query("FROM products WHERE name like %:name% AND category LIKE :category")
    Set<Product> getProductsByNameAndCategory(@Param("name") String name, @Param("category") String category);
}
