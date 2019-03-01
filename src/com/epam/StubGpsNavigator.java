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
import java.util.*;

public class StubGpsNavigator implements GpsNavigator {
    private Set<String> vertexesSet = new HashSet<>();
    private Map<Edge, Integer> matrix = new HashMap<>();
    private Map<Edge, Boolean> isMoreThanOnePath = new HashMap<>();
    private static final int MAX_VALUE = Integer.MAX_VALUE / 2 - 1;
    private List<String> vertexesList;
    private int[][] next;
    private int numberOfVertexes;

    private void inputValidation(String[] path){
        if (path.length != 4) throw new WrongFileFormatException();
        try {
            Integer.valueOf(path[2]);
            Integer.valueOf(path[3]);
        }catch (NumberFormatException e){
            throw new WrongFileFormatException();
        }
    }

    @Override
    public void readData(String filePath) {
        File file = new File(filePath);
        FileReader fr = null;
        BufferedReader br = null;
        String parts[];
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                parts = line.split(" ");
                inputValidation(parts);
                matrix.put(new Edge(parts[0], parts[1]), Integer.valueOf(parts[2]) * Integer.valueOf(parts[3]));
                vertexesSet.add(parts[0]);
                vertexesSet.add(parts[1]);
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
        initMatrix();
    }

    @Override
    public Path findPath(String pointA, String pointB) {
        try {
            if ((!vertexesSet.contains(pointA)) || (!vertexesSet.contains(pointB))){
                throw new ThereIsNoPathException();
            }
            if (matrix.get(new Edge(pointA, pointB)).equals(MAX_VALUE)) {
                throw new ThereIsNoPathException();
            }
            if (isMoreThanOnePath.get(new Edge(pointA, pointB)) != null) {
                throw new MoreThanOnePathException();
            }

            List<String> path = new ArrayList<>();
            int a = vertexesList.indexOf(pointA);
            int b = vertexesList.indexOf(pointB);
            while (a != b) {
                path.add(vertexesList.get(a));
                a = next[a][b];
            }
            path.add(pointB);
            return new Path(path, matrix.get(new Edge(pointA, pointB)));
        }
        catch (ThereIsNoPathException | MoreThanOnePathException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    private void initMatrix(){
        Edge currentEdge, edge1, edge2;
        vertexesList = new ArrayList<>(vertexesSet);
        numberOfVertexes = vertexesSet.size();
        next = new int[numberOfVertexes][numberOfVertexes];

        //matrix init
        for (int i = 0; i < next.length; i++) {
            for (int j = 0; j < next.length; j++) {
                if (i != j) {
                    next[i][j] = j;
                }
            }
        }
        for (int i = 0; i < numberOfVertexes; ++i)
            for (int j = 0; j< numberOfVertexes; ++j){
                currentEdge = new Edge(vertexesList.get(i), vertexesList.get(j));
                if (i == j){
                    matrix.put(currentEdge, 0);
                    continue;
                }
                matrix.putIfAbsent(currentEdge, MAX_VALUE);
            }

        //Floyd-Warshall
        for (int k = 0; k < numberOfVertexes; ++k) {
            for (int i = 0; i < numberOfVertexes; ++i) {
                for (int j = 0; j < numberOfVertexes; ++j) {
                    currentEdge = new Edge(vertexesList.get(i), vertexesList.get(j));
                    edge1 = new Edge(vertexesList.get(i), vertexesList.get(k));
                    edge2 = new Edge(vertexesList.get(k), vertexesList.get(j));
                    if ((matrix.get(edge1) + matrix.get(edge2)) == matrix.get(currentEdge) && (k != j) && (k != i)) {
                        isMoreThanOnePath.put(currentEdge, true);
                    }
                    else if (matrix.get(edge1) + matrix.get(edge2) < matrix.get(currentEdge)) {
                        matrix.put(currentEdge, matrix.get(edge1) + matrix.get(edge2));
                        next[i][j] = next[i][k];
                        isMoreThanOnePath.put(currentEdge, null);
                    }
                }
            }
        }
    }
}