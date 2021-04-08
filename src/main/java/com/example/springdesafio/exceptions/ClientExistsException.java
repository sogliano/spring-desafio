package com.example.springdesafio.exceptions;

public class ClientExistsException extends Exception {
    public ClientExistsException(){
        super("El cliente no se pudo ingresar. Ya existe otro con ese email.");
    }
}