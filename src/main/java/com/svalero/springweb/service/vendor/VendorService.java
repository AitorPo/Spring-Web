package com.svalero.springweb.service.vendor;

import com.svalero.springweb.domain.Shop;
import com.svalero.springweb.domain.Vendor;

import java.util.Optional;
import java.util.Set;

public interface VendorService {

    Set<Vendor> findAll();
    Optional<Vendor> findById(long id);

    Vendor addVendor(Vendor vendor);
    Vendor modifyVendor(long id, Vendor newVendor);
    void deleteVendor(long id);
    Vendor getVendor(long id);
    Vendor patchShop(Shop shop, Vendor newVendor);
    Vendor findBySurname(String surname);
}
