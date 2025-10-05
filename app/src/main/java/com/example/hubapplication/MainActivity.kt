package com.example.hubapplication

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.hubapplication.calculator.CalculatorActivity
import com.example.hubapplication.hoopscore.HoopscoreActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnApp1 = findViewById<LinearLayout>(R.id.btnApp1)
        val btnApp2 = findViewById<LinearLayout>(R.id.btnApp2)
        // val btnApp3 = findViewById<LinearLayout>(R.id.btnApp3)

        btnApp1.setOnClickListener {
            startActivity(Intent(this, HoopscoreActivity::class.java))
        }

        btnApp2.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }
    }
}
