package com.svalero.springweb.service.vendor;

import com.svalero.springweb.domain.Vendor;
import com.svalero.springweb.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class VendorServiceImpl implements VendorService{

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Set<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    @Override
    public Optional<Vendor> findById(long id) {
        return vendorRepository.findById(id);
    }
}
