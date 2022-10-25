package com.cs.asmplugindemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * @author liguandong
 * @data 2022/10/12
 *
 */
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(Constant.TAG, "TestActivity self onCreate: ${javaClass.name}")
        setContentView(R.layout.activity_test2)
        Thread.sleep(1000)
        val btn1 = findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener {
            finish()
        }
        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener {
            try {
                throw java.lang.RuntimeException("test exception")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}