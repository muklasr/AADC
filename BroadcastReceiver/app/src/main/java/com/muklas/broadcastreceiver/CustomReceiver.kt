package com.muklas.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomReceiver : BroadcastReceiver() {
    companion object{
        const val ACTION_CUSTOM_BROADCAST = "com.muklas.broadcastreceiver.CUSTOM"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val intentAction = intent.action as String
        var message : String? = null
        when(intentAction){
            Intent.ACTION_POWER_CONNECTED -> message = "Power Connected"
            Intent.ACTION_POWER_DISCONNECTED -> message = "Power Disconnected"
            ACTION_CUSTOM_BROADCAST -> message = intent.getStringExtra("DATA")
        }
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
