package com.epam;

import java.util.*;
import java.util.function.Function;


public class AdjacencyMatrix<T extends Nodable> {

    public static final int MAX_VALUE = Integer.MAX_VALUE / 2 - 1;
    private Map<T, Integer> matrix = new HashMap<>();
    private Map<T, Boolean> isMoreThanOnePath = new HashMap<>();
    private Set<String> vertexesSet = new HashSet<>();
    private Function<T, Integer> getCost;

    public AdjacencyMatrix(Function<T, Integer> getCost){
        this.getCost = getCost;
    }

    public void put(T edge){
        matrix.put(edge, getCost.apply(edge));
        vertexesSet.add(edge.getV1());
        vertexesSet.add(edge.getV2());
    }

    public void putZero(T edge){
        matrix.put(edge, 0);
    }

    public void updateEdge(T edge, int newCost){
        matrix.put(edge, newCost);
    }

    public int getPrice(T edge){
        return matrix.getOrDefault(edge, MAX_VALUE);
    }

    public void putIfAbsent(T edge){
        matrix.putIfAbsent(edge, MAX_VALUE);
    }

    public List<String> getVertexesList(){
        return new ArrayList<>(vertexesSet);
    }

    public void setMoreThanOnePath(T edge, Boolean bool){
        isMoreThanOnePath.put(edge, bool);
    }

    public boolean isMoreThanOnePath(T edge){
        return isMoreThanOnePath.get(edge) != null;
    }
}
