package com.epam;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;

public class Main {

    public static void main(String[] args) {
        final GpsNavigator navigator = new StubGpsNavigator();
        navigator.readData("D:\\Gps\\road_map.ext");

        final Path path = navigator.findPath("A", "C");
        System.out.println(path);
    }
}



