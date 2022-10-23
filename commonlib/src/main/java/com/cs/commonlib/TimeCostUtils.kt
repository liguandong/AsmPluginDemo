package com.cs.commonlib

import android.util.Log
import androidx.annotation.Keep

@Keep
object TimeCostUtils {
    private const val TAG = "TimeCostUtils"
    private var startTime = 0L

    @JvmStatic
    fun onMethodEnter() {
        startTime = System.currentTimeMillis()
    }
    @JvmStatic
    fun onMethodEnd(className:String,methodName:String) {
        if(System.currentTimeMillis() - startTime >= 500) {
            Log.e(TAG, "className $className methodName $methodName cost : ${System.currentTimeMillis() - startTime}ms")
        }
    }
}