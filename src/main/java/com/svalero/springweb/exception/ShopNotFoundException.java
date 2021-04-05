package com.svalero.springweb.exception;

public class ShopNotFoundException extends RuntimeException{
    public ShopNotFoundException() { super(); }

    public ShopNotFoundException(String message) { super(message); }

    public ShopNotFoundException(long id) { super("Shop not found - id: " + id); }
}
