package crabster.rudakov.sberschoollesson10hwk.figures

import android.graphics.PointF

open class Figure(private var color: Int) {

    private var origin: PointF? = null
    private var current: PointF? = null

    open fun getOrigin(): PointF {
        return origin!!
    }

    open fun setOrigin(origin: PointF) {
        this.origin = origin
    }

    open fun getCurrent(): PointF {
        return current!!
    }

    open fun setCurrent(current: PointF) {
        this.current = current
    }

    open fun getColor(): Int {
        return color
    }

    open fun setColor(color: Int) {
        this.color = color
    }

}