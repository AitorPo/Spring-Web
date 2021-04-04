package com.svalero.springweb.service;

import com.svalero.springweb.domain.Product;
import com.svalero.springweb.exception.ProductNotFoundException;
import com.svalero.springweb.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Set<Product> findProducts(String category) {
        if(category.equals("")){
            return  findAll();
        } else {
            return findByCategory(category);
        }
    }

    @Override
    public Set<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Set<Product> findByCategory(String category) {
        Set<Product> products = productRepository.findByCategory(category);
        return products;
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
}
