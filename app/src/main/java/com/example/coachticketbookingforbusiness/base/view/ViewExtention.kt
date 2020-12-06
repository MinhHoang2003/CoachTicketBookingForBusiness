package com.example.coachticketbookingforbusiness.base.view

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.enable() {
    alpha = 1f
    isEnabled = true
}

fun View.disable() {
    alpha = 0.3f
    isEnabled = false
}