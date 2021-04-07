package com.example.springdesafio.exceptions;

public class InvalidParamException extends Exception {
    public InvalidParamException(){
        super("Entered invalid parameter.");
    }
}
