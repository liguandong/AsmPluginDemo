package com.cs.asmplugindemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @author liguandong
 * @data 2022/10/12
 *
 */
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        Log.i(Constant.TAG, "onCreate: ${javaClass.name}")
    }
}