package com.svalero.springweb.api_config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.repository.ShopRepository;

import java.io.IOException;

/**
 * Clase con la que podemos asignar un objeto Shop a través del shop_id al crear un nuevo objeto Vendor
 */
public class ShopJsonDeserializer extends JsonDeserializer<Shop> {

    private ShopRepository shopRepository;

    public ShopJsonDeserializer(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        if (jsonParser == null) return null;

        long id = Long.parseLong(jsonParser.getText());

        Shop shop = shopRepository.getShop(id);
        return shop;
    }
}
