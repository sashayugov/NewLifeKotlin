package com.example.newlifekotlin.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        java.lang.StringBuilder().apply {
            append("CООБЩЕНИЕ ОТ СИСТЕМЫ\n")
            append("Action: ${p1?.action}")
            toString().also {
            }
        }
    }
}