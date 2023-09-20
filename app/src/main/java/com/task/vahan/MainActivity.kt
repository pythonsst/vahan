package com.task.vahan

import UniversityAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.vahan.R
import com.task.vahan.UniversityRefreshService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var universityAdapter: UniversityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.universityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        universityAdapter = UniversityAdapter(ArrayList()) { url: String ->
            openWebView(url)
        }
        recyclerView.adapter = universityAdapter

        // Initialize WebView settings (moved it here)
        val webView = WebView(this)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Register a broadcast receiver to update the UI when data is refreshed
        registerReceiver(broadcastReceiver, IntentFilter("UNIVERSITY_DATA_REFRESHED"))

        // Start the refresh service
        val serviceIntent = Intent(this, UniversityRefreshService::class.java)
        startService(serviceIntent)

        fetchUniversityData()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the broadcast receiver when the activity is destroyed
        unregisterReceiver(broadcastReceiver)

        // Stop the refresh service when the app is destroyed
        val serviceIntent = Intent(this, UniversityRefreshService::class.java)
        stopService(serviceIntent)
    }

    private fun openWebView(url: String) {
        // Implement opening the WebView here
    }

    private fun fetchUniversityData() {
        // Use RetrofitClient to make the API call
        val universityApiService = RetrofitClient.universityApiService

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = universityApiService.getUniversities().execute()

                if (response.isSuccessful) {
                    val universityListResponse = response.body()

                    if (universityListResponse != null) {
                        val universities = universityListResponse

                        // Update the UI with the fetched data
                        withContext(Dispatchers.Main) {
                            universityAdapter.updateData(universities)
                        }
                    }
                } else {
                    // Handle error response here
                    withContext(Dispatchers.Main) {
                        // Handle the error, e.g., show an error message
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Define the broadcast receiver
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Handle the broadcast here (e.g., update UI when new data is received)
            Log.d("MainActivity", "Data refreshed")
            fetchUniversityData() // You can trigger data fetch here
        }
    }
}
