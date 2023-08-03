package com.test.leftswipeactionview.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.animation.doOnEnd
import com.test.leftswipeactionview.R
import kotlin.math.abs


class SwipeActionView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeActionView)
        text = typedArray.getString(R.styleable.SwipeActionView_text) ?: ""
        textSize = typedArray.getDimension(R.styleable.SwipeActionView_textSize, 60F)
        textColor = typedArray.getColor(R.styleable.SwipeActionView_textColor, 0xFF000000.toInt())
        bgColor = typedArray.getColor(R.styleable.SwipeActionView_backgroundColor, 0xFF00FF00.toInt())
        typedArray.recycle()
    }

    var text = ""
    var textSize = 60F
    var textColor = 0xFF000000.toInt()
    var bgColor = 0xFF00FF00.toInt()

    var callback : Callback? = null

    private val viewHeight = 250

    private var startX = 0F

    private var position: Float = 0.0f
        set(value) {
            field = value
            invalidate()    // redraw
        }

    private val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()

    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        paint.color = bgColor
        canvas.drawRect(-position, 0F, screenWidth - position, viewHeight.toFloat(), paint)
        paint.textSize = textSize
        paint.color = textColor
        canvas.drawText(text, 20F - position, viewHeight / 2 + textSize / 2, paint)
        paint.color = 0xFFFF0000.toInt()
        canvas.drawLine(0F,viewHeight.toFloat(),width.toFloat(),viewHeight.toFloat(),paint) // bottom line
    }

    @SuppressLint("ClickableViewAccessibility")
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
                                callback?.onSwipeFinished()
                            }
                        }.start()
                } else {
                    if (abs(position) < mTouchSlop) {
                        performClick()
                    }
                    ObjectAnimator.ofFloat(this, "position", position, 0F)
                        .start()
                }
                return true
            }

            MotionEvent.ACTION_CANCEL -> {
                ObjectAnimator.ofFloat(this, "position", position, 0F)
                    .start()
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

    interface Callback {
        fun onSwipeFinished()
        // 可拓展更多回调
    }
}