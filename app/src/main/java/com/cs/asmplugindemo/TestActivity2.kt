package com.cs.asmplugindemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @author liguandong
 * @data 2022/10/12
 *
 */
class TestActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(Constant.TAG, "onCreate: ${javaClass.name}")
    }
}