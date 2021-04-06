package com.svalero.springweb.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException() { super(); }

    public CityNotFoundException(String message) { super(message); }

    public CityNotFoundException(long id) { super("Ciudad con id: " + id + " no encontrada"); }
}
