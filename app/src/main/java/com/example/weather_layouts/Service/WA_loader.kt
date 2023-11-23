package com.example.weather_layouts.Service
import android.view.View

class WA_loader(loaderLayout: View) {
    private val loaderLayout: View = loaderLayout
    fun showLoader() {
        loaderLayout.visibility = View.VISIBLE
    }
    fun hideLoader() {
        loaderLayout.visibility = View.GONE
    }
}