package com.svalero.springweb.service;

import com.svalero.springweb.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductService {

    // Método de ProductSeviceImpl que contendrá un if para ejecutar findAll() si no existe category o findByCategory() si sí que existe
    Set<Product> findProducts(String category, String name, Float price);
    // Devuelve todos los productos de la BD
    Set<Product> findAll();
    // Devuelve los productos de la BD filtrados por category
    Set<Product> findByCategory(String category);
    Set<Product> findByCategoryAndNameAndPrice(String category, String name, Float price);
    Set<Product> findByName(String name);
    Set<Product> findByPrice(float price);
    Set<Product> findByCategoryAndName(String category, String name);
    Set<Product> findByCategoryAndPrice(String category, Float price);
    Set<Product> findByNameAndPrice(String name, Float price);
    Set<Product> findByOnSale(boolean onSale);
    Optional<Product> findById(long id);
    Product addProduct(Product product);
    Product modifyProduct(long id, Product newProduct);
    void deleteProduct(long id);
    Set<Product> containsName(String name);
    Set<Product> containsNameAndCategory(String name, String category);
    Product getProduct(long id);
    Object sumProducts(String category);
}
