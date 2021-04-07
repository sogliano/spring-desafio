package com.example.springdesafio.exceptions;

public class InvalidParamException extends Exception {
    public InvalidParamException(String param){
        super("Entered invalid parameter: " + param);
    }
}
