package com.epam;

import com.epam.api.GpsNavigator;
import com.epam.api.Path;
import com.epam.exceptions.MoreThanOnePathException;
import com.epam.exceptions.ThereIsNoPathException;
import com.epam.exceptions.WrongFileFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StubGpsNavigator implements GpsNavigator {
    private AdjacencyMatrix adjacencyMatrix;

    @Override
    public void readData(String filePath) {
        adjacencyMatrix = new AdjacencyMatrix<>(Edge -> Edge.getCost() + Edge.getLength());

        File file = new File(filePath);
        FileReader fr = null;
        BufferedReader br = null;
        String[] parts;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                parts = line.split(" ");
                Utils.inputValidation(parts);
                adjacencyMatrix.put(new Edge(parts[0], parts[1], Integer.valueOf(parts[2]), Integer.valueOf(parts[3])));
            }
        } catch (WrongFileFormatException | IOException e ) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        finally {
            try {
                br.close();
                fr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Path findPath(String pointA, String pointB) {
        FloydWarshall<Edge> floydWarshall = new FloydWarshall<>(Edge.class, adjacencyMatrix);
        try {
            adjacencyMatrix = floydWarshall.calculateWays();
            if ((!adjacencyMatrix.getVertexesList().contains(pointA)) || (!adjacencyMatrix.getVertexesList().contains(pointB))){
                throw new ThereIsNoPathException();
            }
            if (adjacencyMatrix.getPrice(new Edge(pointA, pointB, 0,0)) == (AdjacencyMatrix.MAX_VALUE)) {
                throw new ThereIsNoPathException();
            }
            if (adjacencyMatrix.isMoreThanOnePath(new Edge(pointA, pointB, 0, 0)) == true) {
                throw new MoreThanOnePathException();
            }
            return floydWarshall.getPath(pointA, pointB);
        }
        catch (ThereIsNoPathException  | MoreThanOnePathException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}