package com.svalero.springweb.service.shop;

import com.svalero.springweb.domain.Shop;
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
    public Shop getShop(long id) {
        return null;
    }


}
