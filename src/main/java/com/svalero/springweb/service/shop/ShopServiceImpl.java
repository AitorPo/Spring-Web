package com.svalero.springweb.service.shop;

import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.exception.ShopNotFoundException;
import com.svalero.springweb.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public Set<Shop> findAll() {
        return shopRepository.findAll();
    }

    @Override
    public Optional<Shop> findById(long id) {
        return shopRepository.findById(id);
    }

    @Override
    public Shop addShop(Shop shop) {
        if (shop.getCity() != null)
            return shopRepository.save(shop);

        return null;
    }

    @Override
    public Shop modifyShop(long id, Shop newShop) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ShopNotFoundException(id));
        newShop.setId(shop.getId());
        return shopRepository.save(newShop);
    }

    @Override
    public void deleteShop(long id) {
        shopRepository.findById(id)
                .orElseThrow(() -> new ShopNotFoundException(id));
        shopRepository.deleteById(id);
    }


    @Override
    public Shop getShop(long id) {
        return shopRepository.getShop(id);
    }

}
