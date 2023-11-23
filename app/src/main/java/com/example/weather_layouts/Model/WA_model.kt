package com.example.weather_layouts.Model

data class WeatherResponse(
    val name: String,
    val main: WeatherData,
    val sys: WeatherSysData,
    val coord: WeatherCoordData,
    val wind: WeatherWindData,
    val weather: List<WeatherDescriptionData>
)

data class WeatherData(
    val temp: Double,
    val pressure: Int
)
data class WeatherSysData(
    val country: String
)
data class WeatherCoordData(
    val lat: Double,
    val lon: Double
)
data class WeatherWindData(
    val speed: Double
)
data class WeatherDescriptionData(
    val description: String,
    val id: Int
)
