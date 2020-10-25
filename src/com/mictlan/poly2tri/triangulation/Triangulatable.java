package com.mictlan.poly2tri.triangulation;

import java.util.List;

import com.mictlan.math.geometry.IVector;
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle;

public interface Triangulatable
{
    void prepareTriangulation(TriangulationContext<?> tcx);

    List<DelaunayTriangle> getTriangles();
    List<TriangulationPoint> getPoints();
    void addTriangle(DelaunayTriangle t);
    void addTriangles(List<DelaunayTriangle> list);
    void clearTriangulation();
    
    public TriangulationMode getTriangulationMode();
}
