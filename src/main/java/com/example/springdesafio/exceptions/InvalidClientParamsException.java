package com.example.springdesafio.exceptions;

public class InvalidClientParamsException extends Exception {
    public InvalidClientParamsException(){
        super("Faltan parámetros para ingresar el cliente");
    }
}
