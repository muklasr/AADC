package com.muklas.broadcastreceiver

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mReceiver = CustomReceiver()
    private lateinit var mReceiverComponentName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mReceiverComponentName = ComponentName(this, CustomReceiver::class.java)
        registerReceiver(mReceiver, IntentFilter(CustomReceiver.ACTION_CUSTOM_BROADCAST))

        btnBroadcast.setOnClickListener {
            sendCustomBroadcast()
        }
    }

    private fun sendCustomBroadcast() {
        val broadcastIntent = Intent(CustomReceiver.ACTION_CUSTOM_BROADCAST)
        broadcastIntent.putExtra("DATA", "Data broadcast!")
        sendBroadcast(broadcastIntent)
    }

    override fun onStart() {
        super.onStart()
        packageManager.setComponentEnabledSetting(
            mReceiverComponentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    override fun onStop() {
        super.onStop()
        packageManager.setComponentEnabledSetting(
            mReceiverComponentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }
}
