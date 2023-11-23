package com.example.weather_layouts.Service

import android.app.AlertDialog
import android.content.Context

class WA_warning {
    companion object {
        fun showAlert(context:Context, title: String, message: String) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
            }
            alertDialogBuilder.show()
        }
    }
}
