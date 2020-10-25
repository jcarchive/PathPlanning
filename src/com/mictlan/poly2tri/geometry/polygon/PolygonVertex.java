package com.mictlan.poly2tri.geometry.polygon;


import com.mictlan.poly2tri.triangulation.TriangulationPoint;

public class PolygonVertex extends TriangulationPoint
{
    protected PolygonVertex next;
    protected PolygonVertex previous;
    
    public PolygonVertex(double x, double y, double z )
    {
        super( x, y, z );
    }
}
