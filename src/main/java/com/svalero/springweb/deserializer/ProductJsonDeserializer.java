package com.svalero.springweb.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.svalero.springweb.domain.Product;
import com.svalero.springweb.repository.ProductRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

@Schema(name = "ProductJsonDeserializer", description = "Permite asignar un objeto Product solo con el id")
public class ProductJsonDeserializer extends JsonDeserializer<Product> {

    private ProductRepository productRepository;

    public ProductJsonDeserializer(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser == null) return null;

        long id = Long.parseLong(jsonParser.getText());
        Product product = productRepository.getProduct(id);
        return product;
    }
}
