package com.epam.exceptions;

public class MoreThanOnePathException extends Exception {
    public MoreThanOnePathException(){
        super("There is more than one path");
    }
}
