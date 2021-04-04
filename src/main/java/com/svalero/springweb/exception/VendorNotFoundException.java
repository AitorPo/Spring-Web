package com.svalero.springweb.exception;

public class VendorNotFoundException extends RuntimeException{
    public VendorNotFoundException() { super(); }

    public VendorNotFoundException(String message) { super(message); }

    public VendorNotFoundException(long id) { super("Vendor not found - id: " + id); }
}
