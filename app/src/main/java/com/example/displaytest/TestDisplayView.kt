package com.example.displaytest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class TestDisplayView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }
    private val gridPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private val rows = 8
    private val cols = 5
    private val grid = Array(rows) { BooleanArray(cols) }
    private var cellWidth = 0f
    private var cellHeight = 0f

    init {
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val row = (event.y / cellHeight).toInt()
                val col = (event.x / cellWidth).toInt()
                if (row in 0 until rows && col in 0 until cols) {
                    grid[row][col] = true
                    invalidate()
                    if (isTestComplete()) {
                        (context as MainActivity).showPassButton()
                    }
                }
                performClick() // Add this line
            }
            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        cellWidth = width / cols.toFloat()
        cellHeight = height / rows.toFloat()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (grid[row][col]) {
                    canvas.drawRect(
                        col * cellWidth, row * cellHeight,
                        (col + 1) * cellWidth, (row + 1) * cellHeight, paint
                    )
                }
                canvas.drawRect(
                    col * cellWidth, row * cellHeight,
                    (col + 1) * cellWidth, (row + 1) * cellHeight, gridPaint
                )
            }
        }
    }

    fun startTest() {
        // Initialize the grid with all points not pressed
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                grid[row][col] = false
            }
        }
        invalidate()
    }

    fun isTestComplete(): Boolean {
        for (row in grid) {
            if (row.contains(false)) return false
        }
        return true
    }

    fun resetTest() {
        startTest()
    }

    // Override performClick to handle accessibility events
    override fun performClick(): Boolean {
        super.performClick()
        return true
    }
}
