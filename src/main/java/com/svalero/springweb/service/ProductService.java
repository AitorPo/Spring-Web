package com.svalero.springweb.service;

import com.svalero.springweb.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductService {

    // Método de ProductSeviceImpl que contendrá un if para ejecutar findAll() si no existe category o findByCategory() si sí que existe
    Set<Product> findProducts(String category);
    // Devuelve todos los productos de la BD
    Set<Product> findAll();
    // Devuelve los productos de la BD filtrados por category
    Set<Product> findByCategory(String category);
    Optional<Product> findById(long id);
    Product addProduct(Product product);
    Product modifyProduct(long id, Product newProduct);
    void deleteProduct(long id);
}
