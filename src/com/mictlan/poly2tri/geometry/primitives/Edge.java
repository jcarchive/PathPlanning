package com.mictlan.poly2tri.geometry.primitives;

import com.mictlan.math.geometry.IVector;

public abstract class Edge
{
    protected IVector p;
    protected IVector q;

    public IVector getP()
    {
        return p;
    }

    public IVector getQ()
    {
        return q;
    }
}
