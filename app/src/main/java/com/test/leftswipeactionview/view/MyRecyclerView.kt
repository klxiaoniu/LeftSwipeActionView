package com.test.leftswipeactionview.view

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class MyRecyclerView: RecyclerView {
    constructor(context: android.content.Context) : super(context)
    constructor(context: android.content.Context, attrs: android.util.AttributeSet?) : super(context, attrs)
    constructor(context: android.content.Context, attrs: android.util.AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var startX: Float = 0F
    private var startY: Float = 0F

    // 处理滑动冲突
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - startX
                val dy = event.y - startY
                if (abs(dx) > abs(dy)) {
                    // 横滑，不拦截事件
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

}