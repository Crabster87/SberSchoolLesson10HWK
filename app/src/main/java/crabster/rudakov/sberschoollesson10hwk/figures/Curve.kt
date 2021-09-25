package crabster.rudakov.sberschoollesson10hwk.figures

import android.graphics.Path

class Curve(private var color: Int) : Figure(color) {

    private var path: Path? = null

    fun getPath(): Path {
        return path!!
    }

    fun setPath(path: Path) {
        this.path = path
    }

    override fun getColor(): Int {
        return color
    }

    override fun setColor(color: Int) {
        this.color = color
    }

}