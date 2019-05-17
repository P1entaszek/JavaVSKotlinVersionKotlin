package com.jaszczurowskip.javavskotlinversionkotlin.fps

import android.opengl.GLES10.GL_TRIANGLE_FAN
import android.opengl.GLES10.GL_UNSIGNED_BYTE
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10
import javax.microedition.khronos.opengles.GL11

/**
 * Created by jaszczurowskip on 18.04.2019
 */
internal class Cube {
    private val mFVertexBuffer: FloatBuffer
    private val mColorBuffer: ByteBuffer
    private val mIndexBuffer: ByteBuffer? = null
    private val mTfan1: ByteBuffer
    private val mTfan2: ByteBuffer

    init {
        val vertices = floatArrayOf(
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,

            -1.0f, -1.0f, -1.0f
        )
        val maxColor = 255.toByte()
        val colors = byteArrayOf(
            maxColor,
            maxColor,
            0,
            maxColor,
            0,
            maxColor,
            maxColor,
            maxColor,
            0,
            0,
            0,
            maxColor,
            maxColor,
            0,
            maxColor,
            maxColor,
            maxColor,
            0,
            0,
            maxColor,
            0,
            maxColor,
            0,
            maxColor,
            0,
            0,
            maxColor,
            maxColor,
            0,
            0,
            0,
            maxColor
        )
        val tfan1 = byteArrayOf(1, 0, 3, 1, 3, 2, 1, 2, 6, 1, 6, 5, 1, 5, 4, 1, 4, 0)
        val tfan2 = byteArrayOf(7, 4, 5, 7, 5, 6, 7, 6, 2, 7, 2, 3, 7, 3, 0, 7, 0, 4)
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        mFVertexBuffer = vbb.asFloatBuffer()
        mFVertexBuffer.put(vertices)
        mFVertexBuffer.position(0)
        mColorBuffer = ByteBuffer.allocateDirect(colors.size)
        mColorBuffer.put(colors)
        mColorBuffer.position(0)
        mTfan1 = ByteBuffer.allocateDirect(tfan1.size)
        mTfan1.put(tfan1)
        mTfan1.position(0)
        mTfan2 = ByteBuffer.allocateDirect(tfan2.size)
        mTfan2.put(tfan2)
        mTfan2.position(0)
    }

    fun draw(gl: GL10) {
        gl.glVertexPointer(3, GL11.GL_FLOAT, 0, mFVertexBuffer)
        gl.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 0, mColorBuffer)
        gl.glDrawElements(GL_TRIANGLE_FAN, 6 * 3, GL_UNSIGNED_BYTE, mTfan1)
        gl.glDrawElements(GL_TRIANGLE_FAN, 6 * 3, GL_UNSIGNED_BYTE, mTfan2)
    }
}