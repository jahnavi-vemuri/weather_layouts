package com.example.weather_layouts.Controller
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_layouts.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class Wapp_details : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private var cityName: String? = null
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var wind: Double = 0.0
    private var pressure: Int = 0
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wa_details)
        cityName = intent.getStringExtra("CITY_NAME")
        lat = intent.getDoubleExtra("LATITUDE", 0.0)
        lon = intent.getDoubleExtra("LONGITUDE", 0.0)
        wind = intent.getDoubleExtra("WIND", 0.0)
        pressure = intent.getIntExtra("PRESSURE", 0)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mapOptionButton: ImageButton = findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(this, mapOptionButton)
        popupMenu.menuInflater.inflate(R.menu.map_options, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }
        mapOptionButton.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun changeMap(itemId: Int) {
        when (itemId) {
            R.id.normal_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellite_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.hybrid_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.terrain_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        val location = LatLng(lat, lon)
        val values = "${cityName},${wind}m/s,${pressure}hPa"
        marker = mGoogleMap?.addMarker(MarkerOptions().position(location).title(values))

        mGoogleMap?.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(location, 5f))

    }
}
