package com.epam.exceptions;

public class ThereIsNoSuchVertexException extends Exception{
    public ThereIsNoSuchVertexException(){
        super("There is no such vertex");
    }
}
