package com.example.moviestream.utils

import android.view.View

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.enable(){
    isEnabled = true
}

fun View.disable(){
    isEnabled = false
}