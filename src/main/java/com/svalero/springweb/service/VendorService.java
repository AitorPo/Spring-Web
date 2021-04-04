package com.svalero.springweb.service;

import com.svalero.springweb.domain.Vendor;

import java.util.Optional;
import java.util.Set;

public interface VendorService {

    Set<Vendor> findAll();
    Optional<Vendor> findById(long id);
}
