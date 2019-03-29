package com.epam;

import com.epam.exceptions.WrongFileFormatException;

public class Utils {
    public static void inputValidation(String[] path){
        if (path.length != 4) throw new WrongFileFormatException();
        try {
            Integer.valueOf(path[2]);
            Integer.valueOf(path[3]);
        }catch (NumberFormatException e){
            throw new WrongFileFormatException();
        }
    }
}
