package com.mictlan.math;

public interface IRange<T> {
    T getStart();
    T getEnd();

    void setStart(T s);
    void setEnd(T e);
    T rangeLength();

    boolean isInRangeCC(T value);
    boolean isInRangeCO(T value);
    boolean isInRangeOC(T value);
    boolean isInRangeOO(T value);

    IRange<T> intersection(IRange<T> bx);
}
