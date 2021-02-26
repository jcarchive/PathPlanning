package com.mictlan.math.legacy.geometry;

import processing.core.PVector;

import static com.mictlan.math.utils.ComparatorsUtils.eq;

public interface IPoint {
    float getX();
    float getY();

    void set(float x, float y);
    void setX(float x);
    void setY(float y);

    float distanceTo(IPoint b);

    boolean isAboveOf(IPoint b);
    boolean isBelowOf(IPoint b);

    boolean isRightOf(IPoint b);
    boolean isLeftOf(IPoint b);

    boolean equalsY(IPoint b);
    boolean equalsX(IPoint b);

    static PointOrientation orientation(IPoint p1, IPoint p2, IPoint p3) {
        var d1 = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX());
        var d2 = (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());

        if (eq(d1, d2)) return PointOrientation.COLLINEAR;
        if( d1 < d2)return PointOrientation.LIES_LEFT;

        return PointOrientation.LIES_RIGHT;
    }

    static IPoint middlePoint(IPoint a, IPoint b){
        return new Point((a.getX() + b.getX())/2, (b.getY() + a.getY())/2);
    }
    PVector asVector();
}
