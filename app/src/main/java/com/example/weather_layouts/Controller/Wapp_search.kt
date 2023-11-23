package com.example.weather_layouts.Controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView
import androidx.activity.ComponentActivity
import androidx.core.view.isNotEmpty
import com.example.weather_layouts.R
import com.example.weather_layouts.Service.WA_loader
import com.example.weather_layouts.Service.WA_service
import com.example.weather_layouts.Service.keyboard

class Wapp_search : ComponentActivity() {
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var windSpeed: Double = 0.0
    private var Pressure: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wa_search)

        val loaderLayout = findViewById<View>(R.id.loaderLayout)
        val loader = WA_loader(loaderLayout)
        val rootView = findViewById<View>(android.R.id.content)
        val searchCity = findViewById<SearchView>(R.id.svCity)
        val knowMore = findViewById<Button>(R.id.button)

        rootView.setOnClickListener {
            keyboard.hideKeyboard(searchCity, this)
        }

        searchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    WA_service.alertShown = false
                    loader.showLoader()

                    WA_service.fetchWeatherData(
                        query,
                        findViewById(R.id.cityName),
                        findViewById(R.id.temp),
                        findViewById(R.id.description),
                        null,
                        null,
                        null,
                        null,
                        findViewById(R.id.weatherImage),
                        this@Wapp_search
                    ) { lat, lon, wind, pressure ->
                        loader.hideLoader()
                        keyboard.hideKeyboard(searchCity, this@Wapp_search)
                        latitude = lat
                        longitude = lon
                        windSpeed = wind
                        Pressure = pressure
                        if (searchCity.isNotEmpty()) {
                            knowMore.setOnClickListener {
                                val intent = Intent(this@Wapp_search, Wapp_details::class.java)
                                val cityName = searchCity.query.toString()
                                intent.putExtra("CITY_NAME", cityName)
                                intent.putExtra("LATITUDE", latitude)
                                intent.putExtra("LONGITUDE", longitude)
                                intent.putExtra("WIND", windSpeed)
                                intent.putExtra("PRESSURE", Pressure)
                                startActivity(intent)
                            }
                        }
                    }
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
