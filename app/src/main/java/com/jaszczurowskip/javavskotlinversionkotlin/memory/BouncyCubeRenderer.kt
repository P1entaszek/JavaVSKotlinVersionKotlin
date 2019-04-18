package com.jaszczurowskip.javavskotlinversionkotlin.memory

import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11

/**
 * Created by jaszczurowskip on 18.04.2019
 */
internal class BouncyCubeRenderer : GLSurfaceView.Renderer {
    private val rowSize = 10f
    private val mCube: Cube
    private var mAngle: Float = 0.toFloat()
    private var cubesCount: Int = 0
    private var lastFrameTime: Long = 0
    var fps: Float = 0.toFloat()
    private var width: Int = 0
    private var height: Int = 0

    fun setCubesCount(cubesCount: Int) {
        this.cubesCount = cubesCount

    }

    init {
        mCube = Cube()
        lastFrameTime = System.currentTimeMillis()
    }


    override fun onDrawFrame(gl: GL10) {
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f)
        gl.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        gl.glMatrixMode(GL11.GL_MODELVIEW)
        gl.glEnableClientState(GL11.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL11.GL_COLOR_ARRAY)
        var cubesCounter = (rowSize * rowSize).toInt()
        gl.glLoadIdentity()
        val scaleW = width / rowSize
        val scaleH = height / rowSize
        //        gl.glTranslatef(-rowSizeW/2, -rowSizeH/2, -rowSizeW);
        gl.glTranslatef(scaleW / 2, scaleH / 2, -10f)
        var x = 0
        while (x < rowSize) {
            var y = 0
            while (y < rowSize) {

                gl.glPushMatrix()
                gl.glTranslatef(x * scaleW, y.toFloat() * scaleH, 0.1f)
                //            gl.glScalef(0.9f/rowSizeW, 0.9f/rowSizeH, 0.9f/rowSizeW);
                gl.glRotatef(mAngle, 0.0f, 1.0f, 0.0f)
                gl.glRotatef(mAngle, 1.0f, 0.0f, 0.0f)
                gl.glScalef(scaleW * OBJ_SCALE, scaleW * OBJ_SCALE, scaleW * OBJ_SCALE)
                mCube.draw(gl)
                gl.glPopMatrix()
                cubesCounter--
                y++
            }
            x++
        }
        val currentTime = System.currentTimeMillis()
        val frameTime = currentTime - lastFrameTime
        lastFrameTime = currentTime
        mAngle += 200.8f * (frameTime / 1000f)
        fps = 1000.0f / frameTime.toFloat()
        Log.e("fps", "" + fps)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        this.width = width
        this.height = height
        gl.glViewport(0, 0, width, height)
        val aspectRatio: Float
        val zNear = .1f
        val zFar = 1000f
        val fieldOfView = 60.0f / 57.3f
        val size: Float

        gl.glEnable(GL10.GL_NORMALIZE)
        aspectRatio = width.toFloat() / height.toFloat()
        gl.glMatrixMode(GL10.GL_PROJECTION)
        size = zNear * Math.tan((fieldOfView / 2.0f).toDouble()).toFloat()
        gl.glOrthof(0.0f, width.toFloat(), height.toFloat(), 0.0f, -1.0f, 1000.0f)
        //        gl.glFrustumf(-size, size, -size / aspectRatio,
        //                size / aspectRatio, zNear, zFar);
        gl.glMatrixMode(GL10.GL_MODELVIEW)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        gl.glDisable(GL11.GL_DITHER)
        gl.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST)
        gl.glClearColor(1f, 1f, 1f, 1f)
        gl.glEnable(GL11.GL_CULL_FACE)
        gl.glShadeModel(GL11.GL_SMOOTH)
        gl.glEnable(GL11.GL_DEPTH_TEST)
    }

    companion object {

        val OBJ_SCALE = 0.3f
    }
}