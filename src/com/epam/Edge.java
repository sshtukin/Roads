package com.epam;

import java.util.Objects;

public class Edge {
    private String v1, v2;

    public Edge(String v1, String v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String getV1() {
        return v1;
    }

    public String getV2() {
        return v2;
    }

    @Override
    public String toString() {
        return this.v1 + " " + this.v2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Edge))return false;
        Edge otherEdge = (Edge) obj;
        return (this.v1.equals(otherEdge.v1)) && (this.v2.equals(otherEdge.v2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.v1, this.v2);
    }
}