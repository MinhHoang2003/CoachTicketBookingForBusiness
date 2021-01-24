package com.example.coachticketbookingforbusiness.utils

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

object ToastUtils {

    fun showError(context: Context?, message: String) {
        context?.apply {
            Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
        }
    }

}