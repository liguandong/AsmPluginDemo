package com.cs.commonlib

import android.util.Log
import androidx.annotation.Keep

@Keep
object TimeCostUtils {
    private const val TAG = "TimeCostUtils"
    @JvmStatic
    fun onMethodEnd(className:String,methodName:String,startTime:Long) {
//        if(System.currentTimeMillis() - startTime >= 500) {
            Log.e(TAG, "className $className methodName $methodName cost : ${System.currentTimeMillis() - startTime}ms")
//        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val start = System.currentTimeMillis()
        onMethodEnd("test","init",start)
    }
}