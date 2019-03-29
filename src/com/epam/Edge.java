package com.epam;

public class Edge extends Nodable {
    private String v1, v2;
    private int cost, length;

    public Edge(){
    }

    public Edge(String v1, String v2, int cost, int length) {
        this.v1 = v1;
        this.v2 = v2;
        this.cost = cost;
        this.length = length;
    }

    @Override
    public String getV1() {
        return v1;
    }

    @Override
    public String getV2() {
        return v2;
    }

    @Override
    public void setV1(String v1) {
        this.v1 = v1;
    }

    @Override
    public void setV2(String v2) {
        this.v2 = v2;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return this.v1 + " " + this.v2;
    }
}