package com.mictlan.poly2tri.geometry.polygon;

import java.util.*;
import java.util.stream.Collectors;

import com.mictlan.math.geometry.IVector;
import com.mictlan.poly2tri.triangulation.Meshable;
import com.mictlan.poly2tri.triangulation.TriangulationContext;
import com.mictlan.poly2tri.triangulation.TriangulationMode;
import com.mictlan.poly2tri.triangulation.TriangulationPoint;
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle;

public class Polygon implements Meshable
{

    protected ArrayList<TriangulationPoint> _points = new ArrayList<TriangulationPoint>();
    protected ArrayList<Polygon> _holes;

    protected List<DelaunayTriangle> m_triangles;

    protected PolygonVertex _last;

    public Polygon( Collection<PolygonVertex> points )
    {
        if(points.size() < 3) throw new IndexOutOfBoundsException("Polygon can't be created with less than 3 points");
        _points.addAll( points );
    }

    public static Polygon createPolygon(Collection<IVector> points){
        Collection<PolygonVertex> collection = points
                .stream()
                .map( iVector ->
                    new PolygonVertex(iVector.getX(), iVector.getY(), iVector.getZ())).collect(Collectors.toList()
                );
        return new Polygon(collection);
    }


    public TriangulationMode getTriangulationMode()
    {
        return TriangulationMode.POLYGON;
    }

    public int pointCount()
    {
        int count = _points.size();
        return count;
    }

    public void addHole( Polygon poly )
    {
        if( _holes == null )
        {
            _holes = new ArrayList<Polygon>();
        }
        _holes.add( poly );
    }



    public List<TriangulationPoint> getPoints()
    {
        return _points;
    }

    public ArrayList<Polygon> getHoles() {
        return _holes;
    }

    public List<DelaunayTriangle> getTriangles()
    {
        return m_triangles;
    }
    
    public void addTriangle( DelaunayTriangle t )
    {
        m_triangles.add(t);
    }

    public void addTriangles( List<DelaunayTriangle> list )
    {
        m_triangles.addAll( list );
    }

    public void clearTriangulation()
    {
        if( m_triangles != null )
        {
            m_triangles.clear();
        }
    }

    public void prepareTriangulation( TriangulationContext<?> tcx )
    {
        int hint = _points.size();
        if( _holes != null ) {
            for (Polygon p : _holes) {
                hint += p.pointCount();
            }
        }
        HashMap<TriangulationPoint, TriangulationPoint> uniquePts = new HashMap<TriangulationPoint, TriangulationPoint>(hint);
        TriangulationPoint.extractUniques(uniquePts, _points);
        if( _holes != null ) {
            for (Polygon p : _holes) {
                TriangulationPoint.extractUniques(uniquePts, p._points);
            }
        }
        if( m_triangles == null )
        {
            m_triangles = new ArrayList<DelaunayTriangle>( _points.size() );
        }
        else
        {
            m_triangles.clear();
        }

        // Outer constraints
        for( int i = 0; i < _points.size()-1 ; i++ )
        {
            tcx.newConstraint( _points.get( i ), _points.get( i+1 ) );
        }
        tcx.newConstraint( _points.get( 0 ), _points.get( _points.size()-1 ) );

        // Hole constraints
        if( _holes != null )
        {
            for( Polygon p : _holes )
            {
                for( int i = 0; i < p._points.size()-1 ; i++ )
                {
                    tcx.newConstraint( p._points.get( i ), p._points.get( i+1 ) );
                }
                tcx.newConstraint( p._points.get( 0 ), p._points.get( p._points.size()-1 ) );
            }
        }
        tcx.addPoints(uniquePts.keySet());
    }

}
