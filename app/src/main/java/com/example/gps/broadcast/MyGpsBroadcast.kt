package com.example.gps.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import com.example.gps.Utils.MyData.gpsOnOffLiveData

class MyGpsBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent?.action)){

            val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (isGpsEnabled || isNetworkEnabled){
                gpsOnOffLiveData.postValue(true)
            }else{
                gpsOnOffLiveData.postValue(false)
            }
        }
    }
}