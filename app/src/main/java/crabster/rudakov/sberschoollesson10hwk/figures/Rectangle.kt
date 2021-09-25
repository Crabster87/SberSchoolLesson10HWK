package crabster.rudakov.sberschoollesson10hwk.figures

import android.graphics.PointF

class Rectangle(private var color: Int) : Figure(color) {

    private var origin: PointF? = null
    private var current: PointF? = null

    override fun getOrigin(): PointF {
        return origin!!
    }

    override fun setOrigin(origin: PointF) {
        this.origin = origin
    }

    override fun getCurrent(): PointF {
        return current!!
    }

    override fun setCurrent(current: PointF) {
        this.current = current
    }

    override fun getColor(): Int {
        return color
    }

    override fun setColor(color: Int) {
        this.color = color
    }

}