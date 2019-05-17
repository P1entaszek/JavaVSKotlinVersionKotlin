package com.jaszczurowskip.javavskotlinversionkotlin.fps

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.jaszczurowskip.javavskotlinversionkotlin.R
import kotlinx.android.synthetic.main.activity_graphic_test.*

class GraphicTestActivity : AppCompatActivity() {
    private val handler = Handler()
    private val delay = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphic_test)
        glView.setupView(npCubesAmount.value, npRotateSpeed.value)
        npCubesAmount.setListener { glView.changeAmountOfCubes(npCubesAmount.value, npRotateSpeed.value) }
        npRotateSpeed.setListener { glView.changeAmountOfCubes(npCubesAmount.value, npRotateSpeed.value) }
        updateFPSPeriodically()
    }

    private fun updateFPSPeriodically() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                tvFps.text = glView.framerate.toString()
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks { this.onDestroy() }

    }
}
