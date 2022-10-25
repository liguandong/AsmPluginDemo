package com.cs.asmplugindemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.cs.asmplugindemo.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(Constant.TAG, "MainActivity self onCreate: ")
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener {
            startActivity(Intent(this,TestActivity::class.java))
        }
        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener {
            startActivity(Intent(this,JavaTestActivity::class.java))
        }
        val btn3 = findViewById<Button>(R.id.btn3)
        btn3.setOnClickListener {
            try {
                throw java.lang.RuntimeException("test exception")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}