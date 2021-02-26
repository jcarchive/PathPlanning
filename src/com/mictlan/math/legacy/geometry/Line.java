package com.mictlan.math.legacy.geometry;

import com.mictlan.math.IRange;
import com.mictlan.math.RangeFloat;
import processing.core.PVector;

import static com.mictlan.math.utils.ComparatorsUtils.eq;
import static java.lang.Math.abs;

public class Line implements ILine{
    private IPoint a;
    private IPoint b;

    private  IPoint upper;
    private  IPoint lower;

    private  IPoint left;
    private  IPoint right;

    public Line(IPoint a, IPoint b){
        this.a = a;
        this.b = b;

        calculateRL();
        calculateUL();
    }
    private void calculateUL(){
        if(a.equalsY(b)){
            if(a.isLeftOf(b)){
                upper = a;
            }else{
                upper = b;
            }
        }else if(a.isAboveOf(b)){
            upper = a;
        }else{
            upper = b;
        }
        lower = (a == upper)?b:a;
    }
    private void calculateRL(){
        if(a.equalsX(b)){
            if(a.isAboveOf(b)) left = a;
            else left = b;
        }else{
            if(a.isLeftOf(b)) left = a;
            else left = b;
        }
        right = (a == left)?b:a;

    }
    @Override
    public IPoint getStart() {
        return a;
    }

    @Override
    public IPoint getEnd() {
        return b;
    }

    @Override
    public IPoint getUpper() {
        return upper;
    }

    @Override
    public IPoint getLower() {
        return lower;
    }

    @Override
    public IPoint getRight() {
        return right;
    }

    @Override
    public IPoint getLeft() {
        return left;
    }

    @Override
    public IRange<Float> getYRange() {
       return new RangeFloat(getLower().getY(), getUpper().getY()) ;
    }

    @Override
    public IRange<Float> getXRange() {
        return new RangeFloat(getLeft().getX(), getRight().getX());
    }

    @Override
    public IPoint getYIntersection() {
        return new Point(0, getStart().getY() - getSlope()*getStart().getX());
    }

    @Override
    public IPoint getXIntersection() {
        return new Point( -getYIntersection().getY()/getSlope(),0);
    }

    @Override
    public PVector getDirection() {
        return (new PVector(getEnd().getX() - getStart().getX(), getEnd().getY() - getStart().getY())).normalize();
    }

    @Override
    public PVector getNormal() {
        return (new PVector(-getEnd().getY() + getStart().getY(),getEnd().getX() - getStart().getX()).normalize());
    }

    @Override
    public float getSlope(){
        float m = (getEnd().getY() - getStart().getY())/(getEnd().getX() - getStart().getX());
        if (eq(m,0f)) return 0;
        return m;
    }

    @Override
    public float getH(){
        float h = abs(getEnd().getY() - getStart().getY());
        if (eq(h,0)) return 0;
        return h;
    }

    @Override
    public float getW(){
        float w = abs(getEnd().getX() - getStart().getX());
        if (eq(w,0)) return 0;
        return w;
    }


    @Override
    public boolean isHorizontal(){
        return eq(getH(), 0);
    }
    @Override
    public boolean isVertical(){
        return eq(getW(),0);
    }

    @Override
    public boolean contains(IPoint p) {
        if(isVertical()) return eq(p.getX(), getStart().getX());
        if(isHorizontal()) return eq(p.getY(), getStart().getY());

        return eq(getSlope()*p.getX() + getYIntersection().getY(), p.getY());
    }

    @Override
    public void sortByY() {
        a = upper;
        b = lower;
    }

    @Override
    public void sortByX() {
        a = left;
        b = right;
    }

    @Override
    public PointOrientation orientation(IPoint r) {
        var p = getStart();
        var q = getEnd();

        var d1 = (p.getX() - q.getX())*(q.getY() - r.getY());
        var d2 = (p.getY() - q.getY())*(q.getX() - r.getX());
        if(eq(d1,d2)) return PointOrientation.COLLINEAR;
        if( d1 > d2) return PointOrientation.LIES_RIGHT;
        return PointOrientation.LIES_LEFT;

    }

}
