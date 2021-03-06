package com.svalero.springweb.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException() { super(); }

    public ProductNotFoundException(String message) { super(message); }

    public ProductNotFoundException(long id) { super("Producto con id: " + id + " no encontrado"); }
}
