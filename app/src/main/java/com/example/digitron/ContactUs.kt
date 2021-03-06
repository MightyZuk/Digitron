package com.example.digitron

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.digitron.databinding.ActivityContactUsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ContactUs : AppCompatActivity(),OnMapReadyCallback{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityContactUsBinding.inflate(layoutInflater)
        val view = binding.root
        supportActionBar?.title = "Contact Us"
        window.statusBarColor = ContextCompat.getColor(this,R.color.red)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.red)))
        setContentView(view)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.sendUs.setOnClickListener{
            when {
                binding.name.text.isEmpty() -> {
                    binding.name.error = "Required"
                    binding.name.requestFocus()
                }
                binding.email.text.isEmpty() -> {
                    binding.email.error = "Required"
                    binding.email.requestFocus()
                }
                binding.message.text.isEmpty() -> {
                    binding.message.error = "Required"
                    binding.message.requestFocus()
                }
                !Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches() -> {
                    binding.email.error = "Please provide a valid email"
                    binding.email.requestFocus()
                }
                else -> {
                    Toast.makeText(this,"Sent",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun takeMeToWhatsapp(view: android.view.View) {
        val whatsappUrl = Uri.parse("https://api.whatsapp.com/send/?phone=%2B917709266577&text&app_absent=0")
        startActivity(Intent(Intent.ACTION_VIEW,whatsappUrl))
    }
    fun takeMeToFacebook(view: android.view.View) {
        val facebookUrl = Uri.parse("https://www.facebook.com/DIGITRONLIFE")
        startActivity(Intent(Intent.ACTION_VIEW,facebookUrl))
    }

    fun takeMeToInstagram(view: android.view.View) {
        val instagramUrl = Uri.parse("https://www.instagram.com/digitronssoftwares1/")
        startActivity(Intent(Intent(Intent.ACTION_VIEW,instagramUrl)))
    }

    fun takeMeToLinkedin(view: android.view.View) {
        val linkedinUrl = Uri.parse("https://www.linkedin.com/company/digitron-softwares-and-technology/")
        startActivity(Intent(Intent(Intent.ACTION_VIEW,linkedinUrl)))
    }

    fun takeMeToYoutube(view: android.view.View) {
        val youtubeUrl = Uri.parse("https://www.youtube.com/channel/UCojsSw92BTSIwS_KEqMbusQ")
        startActivity(Intent(Intent.ACTION_VIEW,youtubeUrl))
    }

    fun takeMeToWebsite(view: android.view.View) {
        val websiteUrl = Uri.parse("http://digitronsoftwaresandtechnology.com/")
        startActivity(Intent(Intent.ACTION_VIEW,websiteUrl))
    }

    override fun onMapReady(map: GoogleMap) {
        map.addMarker(
            MarkerOptions().position(LatLng(21.126110238943085, 79.13540831252685))
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            .title("Digitron Software & Technology"))
        Log.d("error","Error")
        map.moveCamera(
            CameraUpdateFactory
            .newLatLngZoom(LatLng(21.126110238943085, 79.13540831252685),16F))
    }


}