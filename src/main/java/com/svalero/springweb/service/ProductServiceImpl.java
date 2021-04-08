package com.svalero.springweb.service;

import com.svalero.springweb.controller.Response;
import com.svalero.springweb.domain.Product;
import com.svalero.springweb.exception.ProductNotFoundException;
import com.svalero.springweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Set<Product> findProducts(String category, String name, Float price) {
            // Existe category
        if(!category.isBlank() && name.isBlank() && price == null){
            return findByCategory(category);
            // Existe name
        } else if(!name.isBlank() && category.isBlank() && price == null){
            return containsName(name);
            // Existe price
        } else if(price != null && category.isBlank() && name.isBlank()){
            return findByPrice(price);
            // Existe category y name
        } else if(!category.isBlank() && !name.isBlank() && price == null){
            return containsNameAndCategory(name, category);
            // Existe category y price
        } else if(!category.isBlank() && name.isBlank() && price != null) {
            return findByCategoryAndPrice(category, price);
            // Existe name y price
        } else if(category.isBlank() && !name.isBlank() && price != null){
            return findByNameAndPrice(name, price);
        } else if (price != null && !category.isBlank() && !name.isBlank()){
            return findByCategoryAndNameAndPrice(category, name, price);
        }

        return findAll();
    }

    @Override
    public Set<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Set<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Set<Product> findByCategoryAndNameAndPrice(String category, String name, Float price) {
        return productRepository.findByCategoryAndNameAndPrice(category, name, price);
    }

    @Override
    public Set<Product> findByOnSale(boolean onSale) {
        return productRepository.findByOnSale(onSale);
    }

    @Override
    public Set<Product> findByPrice(float price) {
        return productRepository.findByPrice(price);
    }

    @Override
    public Set<Product> findByCategoryAndName(String category, String name) {
        return productRepository.findByCategoryAndName(category, name);
    }

    @Override
    public Set<Product> findByCategoryAndPrice(String category, Float price) {
        return productRepository.findByCategoryAndPrice(category, price);
    }

    @Override
    public Set<Product> findByNameAndPrice(String name, Float price) {
        return productRepository.findByNameAndPrice(name, price);
    }

    @Override
    public Set<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product modifyProduct(long id, Product newProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        newProduct.setId(product.getId());
        return productRepository.save(newProduct);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
    }

    @Override
    public Set<Product> containsName(String name) {
        return productRepository.getProducts(name);
    }

    @Override
    public Set<Product> containsNameAndCategory(String name, String category) {
        return productRepository.getProductsByNameAndCategory(name, category);
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.getProduct(id);
    }


    @Override
    public Object sumProducts(String category) {
        if (category.isBlank()) return productRepository.sumAllProducts();

        productRepository.findCategoryName(category)
                .orElseThrow(() -> new ProductNotFoundException("Categoría no encontrada"));

        return productRepository.sumAllProductsByCategory(category);
    }



}
