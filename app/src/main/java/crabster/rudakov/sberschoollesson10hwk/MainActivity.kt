package crabster.rudakov.sberschoollesson10hwk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox

class MainActivity : AppCompatActivity() {

    private lateinit var checkRectangle: CheckBox
    private lateinit var checkLine: CheckBox
    private lateinit var checkCurve: CheckBox
    private lateinit var checkWhite: CheckBox
    private lateinit var checkRed: CheckBox
    private lateinit var checkYellow: CheckBox
    private lateinit var checkZoom: CheckBox
    private lateinit var paint2DViewJava: Paint2DView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkRectangle = findViewById(R.id.check_rectangle)
        checkLine = findViewById(R.id.check_line)
        checkCurve = findViewById(R.id.check_curve)
        checkWhite = findViewById(R.id.check_white)
        checkRed = findViewById(R.id.check_red)
        checkYellow = findViewById(R.id.check_yellow)
        checkZoom = findViewById(R.id.check_zoom)

        setCheckBoxListeners()

        val resetButton: Button = findViewById(R.id.reset_button)
        paint2DViewJava = findViewById(R.id.draw_view)
        resetButton.setOnClickListener { paint2DViewJava.reset() }
    }

    private fun setCheckBoxListeners() {
        checkRectangle.setOnCheckedChangeListener { _, isChecked ->
            checkLine.isChecked = false
            checkCurve.isChecked = false
            checkZoom.isChecked = false
            paint2DViewJava.setCheckedRectangle(isChecked)
        }
        checkLine.setOnCheckedChangeListener { _, isChecked ->
            checkRectangle.isChecked = false
            checkCurve.isChecked = false
            checkZoom.isChecked = false
            paint2DViewJava.setCheckedLine(isChecked)
        }
        checkCurve.setOnCheckedChangeListener { _, isChecked ->
            checkRectangle.isChecked = false
            checkLine.isChecked = false
            checkZoom.isChecked = false
            paint2DViewJava.setCheckedCurve(isChecked)
        }

        checkWhite.setOnCheckedChangeListener { _, isChecked ->
            checkRed.isChecked = false
            checkYellow.isChecked = false
            checkZoom.isChecked = false
            paint2DViewJava.setCheckedWhite(isChecked)
        }
        checkRed.setOnCheckedChangeListener { _, isChecked ->
            checkWhite.isChecked = false
            checkYellow.isChecked = false
            checkZoom.isChecked = false
            paint2DViewJava.setCheckedRed(isChecked)
        }
        checkYellow.setOnCheckedChangeListener { _, isChecked ->
            checkWhite.isChecked = false
            checkRed.isChecked = false
            checkZoom.isChecked = false
            paint2DViewJava.setCheckedYellow(isChecked)
        }
        checkZoom.setOnCheckedChangeListener { _, isChecked ->
            checkRectangle.isChecked = false
            checkLine.isChecked = false
            checkCurve.isChecked = false
            checkWhite.isChecked = false
            checkRed.isChecked = false
            checkYellow.isChecked = false
            paint2DViewJava.setCheckedZoom(isChecked)
        }
    }

}