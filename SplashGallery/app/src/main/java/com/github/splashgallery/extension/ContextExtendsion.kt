package com.github.splashgallery.extension

import android.content.Context
import android.util.Log
import android.widget.Toast

fun Context.toast(message : String, duration : Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}

fun String.dLog(tag : String){
    Log.d(tag ,this)
}