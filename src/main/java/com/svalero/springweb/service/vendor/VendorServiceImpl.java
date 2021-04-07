package com.svalero.springweb.service.vendor;

import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.exception.ShopNotFoundException;
import com.svalero.springweb.exception.VendorNotFoundException;
import com.svalero.springweb.repository.ShopRepository;
import com.svalero.springweb.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class VendorServiceImpl implements VendorService{

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public Set<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @Override
    public Optional<Vendor> findById(long id) {
        return vendorRepository.findById(id);
    }

    @Override
    public Vendor addVendor(Vendor vendor) {
        if (vendor.getShop() != null)
            return vendorRepository.save(vendor);

        return null;
    }

    @Override
    public Vendor modifyVendor(long id, Vendor newVendor) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));
        newVendor.setId(vendor.getId());
        return vendorRepository.save(newVendor);
    }

    @Override
    public void deleteVendor(long id) {
        vendorRepository.findById(id)
                .orElseThrow(() -> new VendorNotFoundException(id));
        vendorRepository.deleteById(id);
    }

    @Override
    public Vendor getVendor(long id) {
        return vendorRepository.getVendor(id);
    }

    @Override
    public Vendor patchShop(Shop shop, Vendor newVendor) {
        Shop finalShop = shop;
        shop = shopRepository.findById(shop.getId()).orElseThrow(() -> new ShopNotFoundException(finalShop.getId()));
        newVendor.setShop(shop);
        return vendorRepository.save(newVendor);
    }

    @Override
    public Vendor findBySurname(String surname) {
        return vendorRepository.findBySurname(surname);
    }

}
