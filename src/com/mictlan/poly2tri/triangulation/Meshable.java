package com.mictlan.poly2tri.triangulation;

import java.util.List;

import com.mictlan.math.geometry.IVector;
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle;

public interface Meshable
{
    void prepareTriangulation(TriangulationContext<?> tcx);

    List<TriangulationPoint> getPoints();
    void addTriangle(DelaunayTriangle t);
    void addTriangles(List<DelaunayTriangle> list);

    TriangulationMode getTriangulationMode();
}
