package com.jaszczurowskip.javavskotlinversionkotlin

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*
import kotlinx.android.synthetic.main.activity_thread_test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.Executors

class ThreadTestActivity : AppCompatActivity() {
    private val imageViewsList = ArrayList<ImageView>()
    private val textViewsList = ArrayList<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_test)
        setupView()
    }

    private fun setupView() {
        textViewsList.add(tvFirstPhoto)
        textViewsList.add(tvSecondPhoto)
        textViewsList.add(tvThirdPhoto)
        textViewsList.add(tvFourthPhoto)
        imageViewsList.add(imgFirstPhoto)
        imageViewsList.add(imgSecondPhoto)
        imageViewsList.add(imgThirdPhoto)
        imageViewsList.add(imgFourthPhoto)
        setupSpinner(spinnerThreadMode!!, R.array.thread_mode)
        registerButtons()
    }

    private fun setupSpinner(spinner: Spinner, array: Int) {
        val adapter = ArrayAdapter.createFromResource(
            this,
            array, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun registerButtons() {
        bttnStart!!.setOnClickListener { v ->
            if (spinnerThreadMode!!.selectedItem == getString(R.string.single_thread)) {
                singleThreadInverse()
            }
            if (spinnerThreadMode!!.selectedItem == getString(R.string.multi_thread)) {
                multiThreadInverse()
            }
            if (spinnerThreadMode!!.selectedItem == getString(R.string.choose_thread_mode)) {
                Toast.makeText(this, R.string.choose_thread_mode, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun singleThreadInverse() {
        var threadTime: Long = 0
        val time = System.currentTimeMillis()
        runBlocking {
            Dispatchers.Main
            for (i in imageViewsList.indices) {
                inversePhotoColors(imageViewsList[i])
                threadTime = System.currentTimeMillis() - time
                textViewsList[i].text = "Converting in: $threadTime ms"
            }
            runOnUiThread { tvAllThreadTime!!.text = "Total converting time in: $threadTime ms" }
        }
    }

    private fun multiThreadInverse() {
        var allThreads: Long = 0
        for (i in imageViewsList.indices) {
            var threadTime: Long
            val time = System.currentTimeMillis()
            val converseDispather = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
            runBlocking {
                launch(converseDispather) {
                    inversePhotoColors(imageViewsList[i])
                    threadTime = System.currentTimeMillis() - time
                    allThreads += threadTime
                    textViewsList[i].text = "Converting in: $threadTime ms"
                }
            }
        }
        allThreads /= 4
        runOnUiThread { tvAllThreadTime!!.text = "Average converting time in: $allThreads ms" }
    }

    private fun inversePhotoColors(img: ImageView) {
        val bitmap = (img.drawable as BitmapDrawable).bitmap
        val length = bitmap.width * bitmap.height
        val array = IntArray(length)
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        mutableBitmap.getPixels(array, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        for (i in 0 until length) {
            array[i] = invertRGB(array[i])
        }
        mutableBitmap.setPixels(array, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        img.setImageBitmap(mutableBitmap)
    }

    private fun invertRGB(rgb: Int): Int { return rgb.inv() and 0x00FFFFFF or -0x1000000 }
}
