package com.mictlan.math.legacy.geometry;

public class LineIntersection {
    public enum Type{
        COLLINEAR,
        POINT,
        NONE,
    }

    private final IPoint start;
    private final IPoint end;
    private final Type type;

    public LineIntersection(IPoint p){
        start = p;
        end = null;
        type = Type.POINT;
    }
    public LineIntersection(IPoint p, IPoint q){
        start = p;
        end = q;
        type = Type.COLLINEAR;
    }

    public LineIntersection(){
        start = null;
        end = null;
        type = Type.NONE;

    }

    public IPoint getPoint(){
        return start;
    }
     public Type getType(){
        return this.type;
     }

    public IPoint getStart(){
        return start;
    }

    public IPoint getEnd(){
        return end;
    }

}
