package com.example.springdesafio.exceptions;

public class ParameterQuantityException extends Exception {
    public ParameterQuantityException(){
        super("Parameter quantity exceeded.");
    }
}
