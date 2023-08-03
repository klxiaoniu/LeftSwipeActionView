package com.test.leftswipeactionview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.core.animation.doOnEnd
import com.test.leftswipeactionview.R
import kotlin.math.abs


class SwipeActionView : View {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: android.util.AttributeSet) : super(context, attrs) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeActionView)
        text = typedArray.getString(R.styleable.SwipeActionView_text) ?: ""
        textSize = typedArray.getDimension(R.styleable.SwipeActionView_textSize, 60F)
        textColor = typedArray.getColor(R.styleable.SwipeActionView_textColor, 0xFF000000.toInt())
        backgroundColor = typedArray.getColor(R.styleable.SwipeActionView_backgroundColor, 0xFF00FF00.toInt())
        typedArray.recycle()
    }

    private var text = ""
    private var textSize = 30F
    private var textColor = 0xFF000000.toInt()
    private var backgroundColor = 0xFF00FF00.toInt()

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
        paint.color = backgroundColor
        canvas.drawRect(-position, 0F, screenWidth - position, viewHeight.toFloat(), paint)
        paint.textSize = textSize
        paint.color = textColor
        canvas.drawText(text, 20F - position, viewHeight / 2 + textSize / 2, paint)
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

    fun recover() {
        position = 0F
        this.visibility = VISIBLE
    }
}