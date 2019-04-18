package com.jaszczurowskip.javavskotlinversionkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jaszczurowskip.javavskotlinversionkotlin.memory.GraphicTestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerListeners()
    }

    private fun registerListeners() {
        bttnFpsTest.setOnClickListener {
            val startFpsTest = Intent(this, GraphicTestActivity::class.java)
            startActivity(startFpsTest)
        }
        bttnAboutApp.setOnClickListener {
            val startFpsTest = Intent(this, AboutApp::class.java)
            startActivity(startFpsTest)
        }
        bttnThreadTest.setOnClickListener {
            val startFpsTest = Intent(this, ThreadTestActivity::class.java)
            startActivity(startFpsTest)
        }
    }

}
