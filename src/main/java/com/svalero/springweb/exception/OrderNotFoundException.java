package com.svalero.springweb.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException() { super(); }

    public OrderNotFoundException(String message) { super(message); }

    public OrderNotFoundException(long id) { super("Pedido con id: " + id + " no encontrado"); }
}
