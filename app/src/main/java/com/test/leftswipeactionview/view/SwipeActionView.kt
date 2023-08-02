package com.test.leftswipeactionview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.animation.doOnEnd
import kotlin.math.abs


class SwipeActionView : View {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: android.util.AttributeSet) : super(context, attrs) {
    }

    private val viewHeight = 250

    private var startX = 0F

    private var position: Float = 0.0f
        set(value) {
            field = value
            invalidate()    // redraw
        }

    private val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()


    override fun onDraw(canvas: Canvas) {
        val paint = Paint()
        paint.color = 0xFF00FF00.toInt()
        canvas.drawRect(-position, 0F, screenWidth - position, viewHeight.toFloat(), paint)
        paint.color = 0xFFFF0000.toInt()
        canvas.drawLine(0F,viewHeight.toFloat(),width.toFloat(),viewHeight.toFloat(),paint) // bottom line
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.rawX
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                position = startX - event.rawX
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (abs(position) > screenWidth / 3) {
                    ObjectAnimator.ofFloat(this, "position", position, screenWidth * if (position > 0) 1F else -1F)
                        .apply {
                            doOnEnd {
                                this@SwipeActionView.visibility = GONE
                            }
                        }.start()
                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
                } else {
                    ObjectAnimator.ofFloat(this, "position", position, 0F)
                        .start()
                }
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, viewHeight)
    }
}