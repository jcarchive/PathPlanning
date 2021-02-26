package com.mictlan.math.legacy.geometry;

import com.mictlan.math.IRange;
import processing.core.PVector;

import static com.mictlan.math.utils.ComparatorsUtils.eq;

public interface ILine {

    IPoint getStart();
    IPoint getEnd();

    IPoint getUpper();
    IPoint getLower();

    IPoint getRight();
    IPoint getLeft();

    IRange<Float> getYRange();
    IRange<Float> getXRange();

    IPoint getYIntersection();
    IPoint getXIntersection();

    PVector getDirection();
    PVector getNormal();

    float getSlope();
    float getH();
    float getW();

    boolean isHorizontal();
    boolean isVertical();

    boolean contains(IPoint p);

    void sortByY();
    void sortByX();

    PointOrientation orientation(IPoint p);

    static LineIntersection extendedIntersection(ILine a, ILine b){
        if(eq(a.getSlope(), b.getSlope())) {
            if(b.contains( a.getStart())) return new LineIntersection(new Point(-Float.NaN, -Float.NaN), new Point(Float.NaN, Float.NaN));
            return new LineIntersection();
        }
        if(a.isHorizontal()){
            if(b.isVertical()){
                return new LineIntersection(new Point(b.getStart().getX(), a.getStart().getY()));
            }
            //TODO
        }else if(a.isVertical()){
            if(b.isHorizontal()){
                return new LineIntersection(new Point(a.getStart().getX(), b.getStart().getY()));
            }
            //TODO
        }
        var b1 = a.getYIntersection().getY();
        var m1 = a.getSlope();

        var b2 = b.getYIntersection().getY();
        var m2 = b.getSlope();

        float x = (b2 - b1)/(m1 - m2);
        float y = m1*x + b1;
        return new LineIntersection(new Point( x, y));
    }

    static LineIntersection intersection(ILine a, ILine b) {
        // Orientation of b endpoints respect this line.
        var bStartOrientation = a.orientation(b.getStart()).getInt();
        var bEndOrientation = a.orientation(b.getEnd()).getInt();

        var aStartOrientation = b.orientation(a.getStart()).getInt();
        var aEndOrientation = b.orientation(a.getEnd()).getInt();

        //b endpoints lie on one side of line a. There is no intersection.
        if(bStartOrientation * bEndOrientation > 0) return new LineIntersection();

        //a endpoints lie on one side of line b. There is no intersection.
        if(aStartOrientation * aEndOrientation > 0) return new LineIntersection();

        if(bStartOrientation == 0 && bEndOrientation == 0){
            var ax = a.getXRange(); //range  |---------------|
            var bx = b.getXRange(); //range  O---------------O
            var ix = ax.intersection(bx);
            IPoint  p,q;
            if(eq(ix.getStart(), ax.getStart())) p = a.getLeft();
            else p = b.getLeft();
            if(eq(ix.getEnd(), ax.getEnd())) q = a.getRight();
            else q = b.getRight();
            return new LineIntersection(p, q);
        }

        return extendedIntersection( a, b);
    }
}
