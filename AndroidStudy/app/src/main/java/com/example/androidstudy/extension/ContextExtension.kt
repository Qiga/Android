package com.example.androidstudy.extension

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.applicationContext, message, duration).show()
}