package com.epam;

import java.util.Objects;

public abstract class Nodable {

    public abstract String getV1();

    public abstract String getV2();

    public abstract void setV1(String v1);

    public abstract void setV2(String v2);

    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Nodable))return false;
        Nodable otherNodable = (Nodable) obj;
        return (this.getV1().equals(otherNodable.getV1())
                        && (this.getV2().equals(otherNodable.getV2())));
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getV1(), this.getV2());
    }
}
