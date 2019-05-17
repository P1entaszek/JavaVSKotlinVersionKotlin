package com.jaszczurowskip.javavskotlinversionkotlin.fps

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jaszczurowskip.javavskotlinversionkotlin.R
import kotlinx.android.synthetic.main.activity_graphic_test.*

class GraphicTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphic_test)
        glView.setupView()
        tvFpsPerformance.text = "FPS performance: 200"
    }
}
