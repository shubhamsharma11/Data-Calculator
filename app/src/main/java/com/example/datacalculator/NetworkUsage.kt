package com.example.datacalculator

import android.content.Context
import android.net.TrafficStats

class NetworkUsage(private val startTime: Long, private val endTime: Long) {

    fun getDataUsage(context: Context?): Long {
        var dataUsage: Long = 0
        if (System.currentTimeMillis() >= startTime && System.currentTimeMillis() <= endTime) {
            val startRxBytes = TrafficStats.getTotalRxBytes()
            val startTxBytes = TrafficStats.getTotalTxBytes()

            // wait for some time
            try {
                Thread.sleep(endTime - System.currentTimeMillis())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val endRxBytes = TrafficStats.getTotalRxBytes()
            val endTxBytes = TrafficStats.getTotalTxBytes()
            dataUsage = endRxBytes - startRxBytes + (endTxBytes - startTxBytes)
        }
        return dataUsage
    }

}