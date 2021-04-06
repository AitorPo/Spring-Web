package com.svalero.springweb.service.shop;

import com.svalero.springweb.domain.Shop;

import java.util.Optional;
import java.util.Set;

public interface ShopService {
    Set<Shop> findAll();
    Optional<Shop> findById(long id);
    Shop addShop(Shop shop);
    Shop modifyShop(long id, Shop newShop);
    void deleteShop(long id);
    Shop getShop(long id);
    Shop patchShop(Shop newShop);
}
