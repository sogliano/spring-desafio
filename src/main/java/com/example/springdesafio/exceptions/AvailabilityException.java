package com.example.springdesafio.exceptions;

public class AvailabilityException extends Exception{
    public AvailabilityException(String name) { super("No hay suficiente " + name + " en stock para realizar la orden."); }
}
