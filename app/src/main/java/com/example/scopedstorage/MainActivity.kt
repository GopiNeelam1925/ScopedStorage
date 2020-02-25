package com.example.scopedstorage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scopedstorage.accessmedialocation.AccessMediaLocationActivity
import com.example.scopedstorage.downlaoddemo.DownloadImageActivity
import com.example.scopedstorage.imagedemo.ImagePickerActivity


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun imagePickerDemo(view : View){
        startActivity(Intent(this@MainActivity, ImagePickerActivity::class.java))
    }

    fun downloadImageDemo(view : View){

            startActivity(Intent(this@MainActivity, DownloadImageActivity::class.java))
        }

    fun accessMediaLocation(view : View){

        startActivity(Intent(this@MainActivity, AccessMediaLocationActivity::class.java))
    }



}

