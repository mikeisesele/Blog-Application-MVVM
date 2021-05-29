package com.decagon.android.sq007.util

import android.app.Activity
import android.util.Log
import android.widget.Toast

fun Activity.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.log(tag: String, message: String){
    Log.i(tag, message)
}