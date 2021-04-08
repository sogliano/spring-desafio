package com.example.springdesafio.exceptions;

public class InvalidArticleException extends Exception{
    public InvalidArticleException(int id){
        super("Entered invalid productId: " + id);
    }
}
