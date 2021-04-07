package com.example.springdesafio.exceptions;

public class InvalidNumberException extends Exception{
    public InvalidNumberException(String param, String number){
        super("Invalid number: " + number + " entered in parameter: " + param);
    }
}
