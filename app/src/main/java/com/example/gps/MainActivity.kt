package com.example.gps

import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gps.Utils.MyData.gpsOnOffLiveData
import com.example.gps.broadcast.MyGpsBroadcast
import com.example.gps.databinding.ActivityMainBinding
import android.provider.Settings
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        gpsBroadcast()

        binding.tvLatlng.setOnClickListener {
            gpsOnOff()
        }

    }

    private fun gpsBroadcast() {
        val myGpsBroadcast = MyGpsBroadcast()
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        this.registerReceiver(myGpsBroadcast, filter)

        gpsOnOffLiveData.observe(this) {
            if (it == true){
                binding.tvLatlng.text = "Gps Yoniq"
            }else{
                binding.tvLatlng.text = "Gps O'chiq"
            }
        }
    }
    fun gpsOnOff(){
        val provider: String =
            Settings.Secure.getString(contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)

        if (provider.contains("gps") == false) { //if gps is disabled
            val poke = Intent()
            poke.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider")
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            sendBroadcast(poke)
        }
    }
}