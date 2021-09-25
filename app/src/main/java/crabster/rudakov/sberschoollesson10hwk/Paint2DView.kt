package crabster.rudakov.sberschoollesson10hwk

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import crabster.rudakov.sberschoollesson10hwk.figures.Curve
import crabster.rudakov.sberschoollesson10hwk.figures.Figure
import crabster.rudakov.sberschoollesson10hwk.figures.Line
import crabster.rudakov.sberschoollesson10hwk.figures.Rectangle
import java.util.ArrayList
import kotlin.math.max
import kotlin.math.min

class Paint2DView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var path = Path()
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = STROKE_WIDTH
    }
    private var figure: Figure? = null
    private val figures: MutableList<Figure> = ArrayList()
    private val curvesArray: SparseArray<Curve> = SparseArray()
    private var isCheckedRectangle = false
    private var isCheckedLine = false
    private var isCheckedCurve = false
    private var isCheckedWhite = false
    private var isCheckedRed = false
    private var isCheckedYellow = false
    private var isCheckedZoom = false
    private var detector: ScaleGestureDetector = ScaleGestureDetector(context, ScaleListener())

    /**
     * Метод отрисовывает фигуры из сформированного общего списка
     */
    override fun onDraw(canvas: Canvas) {
        canvas.scale(scaleFactor, scaleFactor)
        for (x in figures) {
            when (x) {
                is Rectangle -> {
                    drawRectangle(x, canvas)
                }
                is Line -> {
                    drawLine(x, canvas)
                }
                is Curve -> {
                    drawCurve(x, canvas)
                }
            }
        }
    }

    /**
     * Метод описывает алгоритм отрисовки прямоугольников
     */
    private fun drawRectangle(rectangle: Figure, canvas: Canvas) {
        val left = min(rectangle.getOrigin().x, rectangle.getCurrent().x)
        val right = max(rectangle.getOrigin().x, rectangle.getCurrent().x)
        val top = min(rectangle.getOrigin().y, rectangle.getCurrent().y)
        val bottom = max(rectangle.getOrigin().y, rectangle.getCurrent().y)
        val color = rectangle.getColor()
        paint.color = color
        canvas.drawRect(left, top, right, bottom, paint)
    }

    /**
     * Метод описывает алгоритм отрисовки прямых линий
     */
    private fun drawLine(line: Figure, canvas: Canvas) {
        val startX = line.getOrigin().x
        val startY = line.getOrigin().y
        val stopX = line.getCurrent().x
        val stopY = line.getCurrent().y
        val color = line.getColor()
        paint.color = color
        canvas.drawLine(startX, startY, stopX, stopY, paint)
    }

    /**
     * Метод описывает алгоритм отрисовки кривых линий
     */
    private fun drawCurve(curve: Curve, canvas: Canvas) {
        val color = curve.getColor()
        paint.color = color
        val newPath = curve.getPath()
        canvas.drawPath(newPath, paint)
    }

    /**
     * Метод обрабатывает различные события касания
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (verifyZoomAction(event)) return true

        val action = event.action
        val x1 = event.x
        val y1 = event.y
        val current = PointF(x1, y1)

        if ((isCheckedRectangle || isCheckedLine) && (isCheckedWhite || isCheckedRed || isCheckedYellow)) {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    figure = createChosenFigure()
                    setUpPaint(figure!!.getColor())
                    figure!!.setOrigin(current)
                    figures.add(figure!!)
                }
                MotionEvent.ACTION_MOVE -> {
                    figure!!.setCurrent(current)
                    invalidate()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                }
            }
        }
        if (isCheckedCurve && (isCheckedWhite || isCheckedRed || isCheckedYellow)) {
            multiTouchEvent(event)
        }
        return true
    }

    /**
     * Метод обрабатывает событие многопальцевого касания в случае
     * рисования кривых
     */
    private fun multiTouchEvent(event: MotionEvent) {
        val actionMasked = event.actionMasked
        val actionIndex = event.actionIndex
        var pointerId = event.getPointerId(actionIndex)

        var curve: Curve?

        when (actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                curve = createChosenFigure() as Curve?
                setUpPaint(curve!!.getColor())
                figures.add(curve)
                path = Path()
                curve.setPath(path)
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                curvesArray.put(pointerId, curve)
            }
            MotionEvent.ACTION_MOVE -> {
                for (index in 0 until event.pointerCount) {
                    pointerId = event.getPointerId(index)
                    curve = curvesArray[pointerId]
                    val pointerPath = curve.getPath()
                    pointerPath.lineTo(event.getX(index), event.getY(index))
                    curve!!.setPath(pointerPath)
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> { }
        }
    }

    /**
     * Метод проверяет значение от CheckBox 'Zoom' и возвращает
     * 'true', если событие было обработано
     */
    private fun verifyZoomAction(event: MotionEvent): Boolean {
        if (isCheckedZoom) {
            if (detector.onTouchEvent(event)) {
                return true
            }
        }
        return false
    }

    /**
     * Метод проверяет значения от CheckBox и создаёт фигуру по
     * заданным параметрам
     */
    private fun createChosenFigure(): Figure? {
        var figure: Figure? = null
        if (isCheckedRectangle) {
            if (isCheckedWhite) figure = Rectangle(Color.WHITE)
            if (isCheckedRed) figure = Rectangle(Color.RED)
            if (isCheckedYellow) figure = Rectangle(Color.YELLOW)
        } else if (isCheckedLine) {
            if (isCheckedWhite) figure = Line(Color.WHITE)
            if (isCheckedRed) figure = Line(Color.RED)
            if (isCheckedYellow) figure = Line(Color.YELLOW)
        } else if (isCheckedCurve) {
            if (isCheckedWhite) figure = Curve(Color.WHITE)
            if (isCheckedRed) figure = Curve(Color.RED)
            if (isCheckedYellow) figure = Curve(Color.YELLOW)
        }
        return figure
    }

    /**
     * Метод устанавливает параметры рисования, устанавливая переданный цвет
     */
    private fun setUpPaint(color: Int) {
        paint.color = color
    }

    /**
     * Метод очищает список фигур и перерисовывает View
     */
    fun reset() {
        figures.clear()
        curvesArray.clear()
        invalidate()
    }

    /**
     * Создаём сеттеры для получения значений от CheckBox
     */
    fun setCheckedRectangle(checkedRectangle: Boolean) {
        isCheckedRectangle = checkedRectangle
    }

    fun setCheckedLine(checkedLine: Boolean) {
        isCheckedLine = checkedLine
    }

    fun setCheckedCurve(checkedCurve: Boolean) {
        isCheckedCurve = checkedCurve
    }

    fun setCheckedWhite(checkedWhite: Boolean) {
        isCheckedWhite = checkedWhite
    }

    fun setCheckedRed(checkedRed: Boolean) {
        isCheckedRed = checkedRed
    }

    fun setCheckedYellow(checkedYellow: Boolean) {
        isCheckedYellow = checkedYellow
    }

    fun setCheckedZoom(checkedZoom: Boolean) {
        isCheckedZoom = checkedZoom
    }

    /**
     * Создаём вложенный класс для описания логики увеличения View
     */
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        /**
         * Метод задаёт параметры увеличения и производит отрисовку
         */
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor
            scaleFactor = max(minScale, min(scaleFactor, maxScale))
            invalidate()
            return super.onScale(detector)
        }

    }

    /**
     * Храним константы
     */
    companion object {
        private const val STROKE_WIDTH = 10f
        private var scaleFactor: Float = 1.0f
        private var minScale: Float = 0.2f
        private var maxScale: Float = 5.0f
    }

}