package com.example.weather_layouts.Service

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.weather_layouts.Config.WA_config
import com.example.weather_layouts.Model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.weather_layouts.R

interface WA_service {
    @GET("weather")
    fun getWeatherData(
        @Query("q") cityName: String?,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

    companion object {
        var alertShown = false

        fun fetchWeatherData(
            cityName: String?,
            cityTextView: TextView?,
            tempTextView: TextView?,
            descriptionTextView: TextView?,
            latTextView: TextView?,
            lonTextView: TextView?,
            windTextView: TextView?,
            pressureTextView: TextView?,
            symbolImageView: ImageView,
            context: Context,
            callback: (Double, Double, Double, Int) -> Unit
        ) {
            if (alertShown) {
                return
            }
            val retrofit = Retrofit.Builder()
                .baseUrl(WA_config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(WA_service::class.java)
            val call: Call<WeatherResponse> = service.getWeatherData(cityName, WA_config.API_KEY)
            Log.i("MYTAG", "hello boss")
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    Log.i("MYTAG", "hello world")
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        if (weatherData != null) {
                            cityTextView?.text = weatherData?.name
                            val temperatureInCelsius =
                                weatherData?.main?.temp?.toFloat()?.minus(273.15f)
                            val formattedTemperature = "%.2f".format(temperatureInCelsius)
                            tempTextView?.text = "${formattedTemperature?.toString()}"
                            descriptionTextView?.text =
                                weatherData?.weather?.get(0)?.description.toString()
                            latTextView?.text = "Lat: ${weatherData?.coord?.lat.toString()}"
                            lonTextView?.text = "Lon: ${weatherData?.coord?.lon.toString()}"
                            windTextView?.text= "Wind: ${weatherData?.wind?.speed.toString()}"
                            pressureTextView?.text="Pressure:${weatherData?.main?.pressure.toString()}"
                            val weatherConditionId = weatherData.weather[0]?.id ?: -1
                            Log.i("Mytag", weatherConditionId.toString())
                            val weatherImages = WA_images()
                            val weatherImageId =
                                weatherImages.getWeatherConditionImageId(weatherConditionId)
                            symbolImageView.setImageResource(weatherImageId)
                            val lat = weatherData.coord?.lat ?: 0.0
                            val lon = weatherData.coord?.lon ?: 0.0
                            val wind = weatherData.wind?.speed ?: 0.0
                            val pressure=weatherData.main.pressure ?: 0
                            // Invoke the callback with lat and lon
                            callback(lat, lon, wind, pressure)
                        } else {
                            if (!alertShown) {
                                WA_warning.showAlert(
                                    context,
                                    context.getString(R.string.title),
                                    context.getString(R.string.data)
                                )
                                alertShown = true
                            }
                        }
                    } else {
                        if (!alertShown) {
                            WA_warning.showAlert(
                                context,
                                context.getString(R.string.title),
                                context.getString(R.string.cityName)
                            )
                            alertShown = true
                        }
                    }
                }
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.i("Error", t.toString())
                    if (!alertShown) {
                        WA_warning.showAlert(
                            context,
                            context.getString(R.string.title),
                            context.getString(R.string.apifailed)
                        )
                        alertShown = true
                    }
                }
            })
        }
    }
}

