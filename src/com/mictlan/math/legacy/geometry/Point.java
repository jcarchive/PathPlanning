package com.mictlan.math.legacy.geometry;

import processing.core.PVector;

import static com.mictlan.math.utils.ComparatorsUtils.*;
import static java.lang.Math.sqrt;

public class Point implements IPoint, Comparable<Point>{
    private final PVector p;
    public Point(float x, float y){
        p = new PVector(x,y);
    }

    public Point(PVector p) {
        this.p = p.copy();
    }

    @Override
    public float getX() {
        return p.x;
    }

    @Override
    public float getY() {
        return p.y;
    }

    @Override
    public void set(float x, float y){
        p.set( x, y);
    }

    @Override
    public void setX(float x) {
        p.x = x;
    }

    @Override
    public void setY(float y) {
        p.y = y;
    }

    @Override
    public float distanceTo(IPoint b) {
        float dx = b.getX() - getX();
        float dy = b.getY() - getY();
        return (float) sqrt(dx*dx + dy*dy);
    }

    @Override
    public boolean isAboveOf(IPoint b) {
        return gt(getY(), b.getY());
    }

    @Override
    public boolean isBelowOf(IPoint b) {
        return lt(getY(), b.getY());
    }

    @Override
    public boolean isRightOf(IPoint b) {
        return gt(getX(), b.getX());
    }

    @Override
    public boolean isLeftOf(IPoint b) {
        return lt(getX(),b.getX());
    }

    @Override
    public boolean equalsY(IPoint b) {
        return eq(b.getY(), getY());
    }

    @Override
    public boolean equalsX(IPoint b) {
        return eq(b.getY(), getY());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj  instanceof IPoint)) return false;
        var b = (IPoint)obj;
        return equalsX(b) && equalsY(b);
    }

    @Override
    public int compareTo(Point point) {
        if(equalsY(point))
            if(getX() < point.getX()) return 1;
        if( getY() > point.getY()) return 1;
        return -1;
    }
    @Override
    public PVector asVector(){
        return this.p;
    }
}
