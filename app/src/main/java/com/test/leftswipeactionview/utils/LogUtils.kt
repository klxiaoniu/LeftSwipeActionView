package com.test.leftswipeactionview.utils

import android.util.Log

class LogUtils {
    companion object {
        private const val TAG = "SwipeActionView"
        fun i(msg: Any) = Log.i(TAG, msg.toString())
    }
}