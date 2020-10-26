package com.mictlan.mvc.views

import com.mictlan.math.geometry.IVector
import com.mictlan.mvc.GlobalSettings
import com.mictlan.mvc.models.Map
import com.mictlan.mvc.utils.Color
import processing.core.PGraphics
import com.mictlan.mvc.utils.fill
import com.mictlan.mvc.utils.stroke
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle
import processing.core.PApplet

class MapView(model: Map, guid: String): View<Map>(model, guid) {
    var graphCanvas: PGraphics = context.createGraphics(context.width, context.height)
    private var showMesh: Boolean = true
    private var showGraph: Boolean = true
    private var showPath: Boolean = true

    fun drawBuffer(canvas: PGraphics){
        canvas.push()
        canvas.stroke(255)
        canvas.strokeWeight(5f)
        canvas.noFill()
        canvas.beginShape()
        model.buffer.forEach {p -> canvas.vertex(p.x.toFloat(), p.y.toFloat()) }
        canvas.endShape()
        canvas.pop()
    }

    private fun drawLines(vertex: Collection<IVector>){
        canvas.push()
        canvas.beginShape()
        vertex.forEach { t -> canvas.vertex( t.x.toFloat(), t.y.toFloat())}
        canvas.vertex(vertex.first().x.toFloat(), vertex.first().y.toFloat())
        canvas.endShape()
        canvas.pop()
    }

    private fun drawShape(vertex: Collection<DelaunayTriangle>){
        canvas.push()
        canvas.beginShape(PGraphics.TRIANGLES)
        vertex.forEach { t ->
            for (point: IVector in t.points) {
                canvas.vertex( point.x.toFloat(), point.y.toFloat())
            }
        }
        canvas.endShape()
        canvas.pop()
    }

    private fun drawPolygonMesh(polygon: com.mictlan.poly2tri.geometry.polygon.Polygon){
        canvas.push()
        canvas.noFill()
        canvas.stroke(255)
        if(polygon.triangles != null)drawShape(polygon.triangles)
        canvas.pop()

    }

    private fun drawPolygon(polygon: com.mictlan.poly2tri.geometry.polygon.Polygon, fill: Color? = null){
        canvas.push()
        if(fill != null) canvas.noStroke()
        else canvas.stroke(100f,100f,100f)

        if(fill != null) canvas.fill(fill)
        else canvas.noFill()
        drawLines(polygon.points)
        canvas.pop()

        canvas.push()
        canvas.smooth()
        canvas.noStroke()
        polygon.holes?.forEach{drawPolygon(it, GlobalSettings.ObstaclesColor)}
        canvas.pop()


    }

    private fun drawMap(canvas: PGraphics){
        drawPolygon(model.mapGeometry!!); genGraphView()
        if(showMesh)
            drawPolygonMesh(model.mapGeometry!!)
        if(showGraph)
            canvas.image(graphCanvas, 0f, 0f)
        if(model.goal != null) {
            canvas.fill(0f, 255f, 0f)
            canvas.noStroke()
            canvas.ellipse(model.goal!!.point.x.toFloat(), model.goal!!.point.y.toFloat(), 20f, 20f)
        }

        if(model.start != null) {
            canvas.fill(0f, 0f, 255f)
            canvas.noStroke()
            canvas.ellipse(model.start!!.point.x.toFloat(), model.start!!.point.y.toFloat(), 20f, 20f)
        }
    }

    private fun drawPath(canvas: PGraphics){
        canvas.noFill()
        canvas.strokeWeight(10f)
        canvas.beginShape()
        if(model.goal?.parent != null && model.start != null){
            var current = model.goal;
            while(current != null){
                canvas.vertex(current.point.x.toFloat(), current.point.y.toFloat())
                current = current.parent
            }
        }
        canvas.endShape()
    }
    override fun draw(canvas: PGraphics) {
        canvas.clear()
        drawBuffer(canvas)
        if(model.buffer.count() >= 3) drawBuffer(canvas)
        if(model.mapGeometry != null) {
            canvas.push()
            drawMap(canvas)
            canvas.pop()
            if(showPath){
                canvas.push()
                drawPath(canvas)
                canvas.pop()
            }

        }

        canvas.fill(GlobalSettings.TextColor)
        if(model.buffer.count() > 0)
            canvas.text("Edit: ${model.buffer.last()}", (canvas.width / 2).toFloat(), (canvas.height - 20).toFloat())
        else
            canvas.text("Edit: ", (canvas.width / 2).toFloat(), (canvas.height - 20).toFloat())
        canvas.imageMode(PApplet.CORNER)
    }

    private fun genGraphView(){
        if(model.mapGeometry?.triangles == null) return;
        graphCanvas.beginDraw()
        graphCanvas.clear()
        graphCanvas.strokeWeight(6f)
        graphCanvas.stroke(GlobalSettings.TextColor)
        graphCanvas.noFill()
        graphCanvas.beginShape(PApplet.LINES)
        model.graphs.values
                .forEach{
                    graph -> graph.neighbors
                        .forEach{
                            neighbor ->
                                graphCanvas.ellipse(graph.point.x.toFloat(), graph.point.y.toFloat(), 10f ,10f)
                                graphCanvas.vertex(graph.point.x.toFloat(), graph.point.y.toFloat())
                                graphCanvas.vertex(neighbor.point.x.toFloat(), neighbor.point.y.toFloat())
                        }
                }
        for (triangle in model.mapGeometry!!.triangles) {
            if(!triangle.isInterior) continue
            val current = triangle.centroid()
            for (neighbor in triangle.neighbors) {
                if(neighbor == null || !neighbor.isInterior) continue
                val next = neighbor.centroid()
                graphCanvas.vertex(current.x.toFloat(), current.y.toFloat())
                graphCanvas.vertex(next.x.toFloat(), next.y.toFloat())
            }
        }
        graphCanvas.endShape()
        graphCanvas.endDraw()

    }

    override fun toggleLayer(index: Int) {
        when(index){
            '2'.toInt() -> {showPath = !showPath; println("Edit: ShowPath $showPath")}
            '1'.toInt() -> { showMesh = !showMesh; println("Edit: ShowMesh $showMesh")}
            '0'.toInt() -> { showGraph= !showGraph; println("Edit: ShowGraph $showGraph")}
        }

    }
}