package com.epam.exceptions;

public class WrongFileFormatException extends RuntimeException {
    public WrongFileFormatException(){
        super("Wrong file format");
    }
}
