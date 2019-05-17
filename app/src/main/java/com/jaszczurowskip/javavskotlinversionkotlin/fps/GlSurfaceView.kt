package com.jaszczurowskip.javavskotlinversionkotlin.fps

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * Created by jaszczurowskip on 18.04.2019
 */
class GlSurfaceView : GLSurfaceView {

    private lateinit var myRender: BouncyCubeRenderer

    val framerate: Float
        get() = myRender.fps

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    fun setupView() {
        super.setEGLConfigChooser(8, 8, 8, 8, 16, 0)
        this.myRender = BouncyCubeRenderer()
        myRender.setCubesCount(100)
        setRenderer(myRender)
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}