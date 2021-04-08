package com.example.springdesafio.exceptions;

public class EmptyCartException extends Exception {
    public EmptyCartException(){
        super("El carrito de compras está vacío. Crea una solicitud.");
    }
}
