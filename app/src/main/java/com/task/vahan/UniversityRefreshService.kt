package com.task.vahan
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter


class UniversityRefreshService : Service() {
    private val handler = Handler()
    private val refreshInterval = 10 * 1000 // 10 seconds in milliseconds
    private val broadcastIntent = Intent("UNIVERSITY_DATA_REFRESHED")

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Register a broadcast receiver
        registerReceiver(broadcastReceiver, IntentFilter("UNIVERSITY_DATA_REFRESHED"))

        // Schedule periodic data refresh using a Handler
        handler.postDelayed({
            // Call the function to fetch university data
            fetchUniversityData()

            // Reschedule the refresh
;
            handler.postDelayed({
                // Call the function to fetch university data
                fetchUniversityData()

                // Reschedule the refresh
                handler.postDelayed({
                    // Call this lambda again for periodic refresh
                    fetchUniversityData()
                }, refreshInterval.toLong())
            }, refreshInterval.toLong())


        }, refreshInterval.toLong())

        return START_STICKY
    }

    private fun fetchUniversityData() {
        // Use RetrofitClient to make the API call
        val universityApiService = RetrofitClient.universityApiService

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = universityApiService.getUniversities().execute()

                if (response.isSuccessful) {
                    val universityListResponse = response.body()

                    if (universityListResponse != null) {
                        val universities = universityListResponse
                        // Send a broadcast to notify the MainActivity about the new data
                        sendBroadcast(broadcastIntent)
                        Log.d("UniversityRefreshService", "Data refreshed: $universities")
                    }
                } else {
                    // Handle error response here
                    Log.e("UniversityRefreshService", "API request failed: ${response.code()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("UniversityRefreshService", "Error during data refresh: ${e.message}")
            }
        }
    }

    // Unregister the broadcast receiver when the service is destroyed
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    // Define the broadcast receiver
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Handle the broadcast here (e.g., update UI in the MainActivity)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
