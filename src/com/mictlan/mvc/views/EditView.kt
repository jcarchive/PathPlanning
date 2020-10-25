package com.mictlan.mvc.views

import com.mictlan.math.geometry.IVector
import com.mictlan.mvc.GlobalSettings
import com.mictlan.mvc.models.EditManager
import processing.core.PGraphics
import com.mictlan.mvc.utils.fill
import com.mictlan.poly2tri.Poly2Tri
import com.mictlan.poly2tri.geometry.polygon.Polygon

class EditView(model: EditManager, guid: String): View<EditManager>(model, guid) {
    var polygon: Polygon? = null

    fun drawBuffer(canvas: PGraphics){
        canvas.push()
        canvas.noFill()
        canvas.stroke(255)
        canvas.strokeWeight(5.toFloat())
        canvas.beginShape()
        model.buffer.forEach {p -> canvas.vertex(p.x.toFloat(), p.y.toFloat()) }
        canvas.endShape()
        canvas.pop()
    }

    fun drawTriangulation(canvas: PGraphics){
        canvas.push()
        canvas.noFill()
        canvas.stroke(255)
        canvas.beginShape(PGraphics.TRIANGLES)
        polygon?.triangles?.forEach { t ->
            for (point: IVector in t.points) {
                canvas.vertex( point.x.toFloat(), point.y.toFloat())
            }
        }
        canvas.endShape()
        canvas.pop()
    }
    override fun draw(canvas: PGraphics) {
        canvas.clear()
        drawBuffer(canvas)
        if(model.buffer.count() >= 3) drawTriangulation(canvas)
        canvas.fill(GlobalSettings.TextColor)
        canvas.text("Edit:", (canvas.width / 2).toFloat(), (canvas.height - 20).toFloat())
    }

    override fun update() {
        if(model.buffer.count() >= 3) {
            polygon = Polygon.createPolygon(model.buffer)
            Poly2Tri.triangulate(polygon)
        }
        super.update()

    }
}