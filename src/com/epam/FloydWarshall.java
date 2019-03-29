package com.epam;

import com.epam.api.Path;

import java.util.*;

public class FloydWarshall<T extends Nodable>{

    private int[][] next;
    private int numberOfVertexes;
    private T currentEdge, edge1, edge2;
    private Class<T> clazz;
    private AdjacencyMatrix<T> adjacencyMatrix;

    public FloydWarshall(Class<T> clazz, AdjacencyMatrix<T> adjacencyMatrix){
        this.clazz = clazz;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public AdjacencyMatrix<T> calculateWays() throws IllegalAccessException, InstantiationException {
        List<String> vertexesList = adjacencyMatrix.getVertexesList();
        numberOfVertexes = vertexesList.size();
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
                currentEdge = clazz.newInstance();
                currentEdge.setV1(vertexesList.get(i));
                currentEdge.setV2(vertexesList.get(j));

                if (i == j){
                    adjacencyMatrix.putZero(currentEdge);
                    continue;
                }
                adjacencyMatrix.putIfAbsent(currentEdge);
            }


        //Floyd-Warshall
        for (int k = 0; k < numberOfVertexes; ++k) {
            for (int i = 0; i < numberOfVertexes; ++i) {
                for (int j = 0; j < numberOfVertexes; ++j) {
                    currentEdge = (T) clazz.newInstance();
                    edge1 = (T) clazz.newInstance();
                    edge2 = (T) clazz.newInstance();

                    currentEdge.setV1(vertexesList.get(i));
                    currentEdge.setV2(vertexesList.get(j));

                    edge1.setV1(vertexesList.get(i));
                    edge1.setV2(vertexesList.get(k));

                    edge2.setV1(vertexesList.get(k));
                    edge2.setV2(vertexesList.get(j));

                    if ((adjacencyMatrix.getPrice(edge1) + adjacencyMatrix.getPrice(edge2)) == adjacencyMatrix.getPrice(currentEdge) && (k != j) && (k != i)) {
                        adjacencyMatrix.setMoreThanOnePath(currentEdge, true);
                    }
                    else if (adjacencyMatrix.getPrice(edge1) + adjacencyMatrix.getPrice(edge2) < adjacencyMatrix.getPrice(currentEdge)) {
                        adjacencyMatrix.updateEdge(currentEdge, adjacencyMatrix.getPrice(edge1) + adjacencyMatrix.getPrice(edge2));
                        next[i][j] = next[i][k];
                        adjacencyMatrix.setMoreThanOnePath(currentEdge, null);
                    }
                }
            }
        }
        return adjacencyMatrix;
    }


    public Path getPath(String pointA, String pointB) throws IllegalAccessException, InstantiationException {
        List<String> vertexesList = adjacencyMatrix.getVertexesList();
        List<String> path = new ArrayList<>();
        int a = vertexesList.indexOf(pointA);
        int b = vertexesList.indexOf(pointB);

        while (a != b) {
            path.add(vertexesList.get(a));
            a = next[a][b];
        }
        path.add(pointB);
        currentEdge = (T) clazz.newInstance();
        currentEdge.setV1(pointA);
        currentEdge.setV2(pointB);
        return new Path(path, adjacencyMatrix.getPrice(currentEdge));
    }
}
