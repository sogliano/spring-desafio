package com.example.springdesafio.exceptions;

public class InvalidNumberException extends Exception{
    public InvalidNumberException(String param){
        super("Invalid number entered in parameter: " + param);
    }
}
