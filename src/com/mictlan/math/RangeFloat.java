package com.mictlan.math;

import static com.mictlan.math.utils.ComparatorsUtils.*;
import static java.lang.Math.abs;

public class RangeFloat implements IRange<Float>{
    private float s;
    private float e;

    public RangeFloat(float s, float e){
        this.s = s;
        this.e = e;
    }

    @Override
    public Float getStart() {
        return s;
    }

    @Override
    public Float getEnd() {
        return e;
    }

    @Override
    public void setStart(Float s) {
        this.s = s;
    }

    @Override
    public void setEnd(Float e) {
        this.e = e;
    }

    @Override
    public Float rangeLength() {
        return e - s;
    }

    @Override
    public boolean isInRangeCC(Float value) {
        return eq(value, s) || eq(value, e) || (gt(value, s) && lt(value, e));
    }

    @Override
    public boolean isInRangeCO(Float value) {
        return eq(value, s) || ( gt(value, s) && lt(value, e));
    }

    @Override
    public boolean isInRangeOC(Float value) {
        return eq(value, e) ||  gt(value, s) && lt(value, e);
    }

    @Override
    public boolean isInRangeOO(Float value) {
        return gt(value, s) && lt(value, e);
    }

    @Override
    public IRange<Float> intersection(IRange<Float> b) {
        IRange<Float> min, max;
        if(b.getStart() < getStart()) {
            min = b;
            max = this;
        } else {
            min = this;
            max = b;
        }
        if(abs(max.getStart() - min.getStart()) > min.rangeLength()) return null;
        if(max.getEnd() > min.getEnd())return new RangeFloat(max.getStart(), min.getEnd());

        return new RangeFloat(max.getStart(), max.getEnd());



    }
}
