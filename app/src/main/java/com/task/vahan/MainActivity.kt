package com.task.vahan

import UniversityAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material3.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private var recyclerView: RecyclerView? = null
    private var universityAdapter: UniversityAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.universityRecyclerView)
        recyclerView?.setLayoutManager(LinearLayoutManager(this))
        universityAdapter = UniversityAdapter(ArrayList<University>()) { url: String ->
            openWebView(
                url
            )
        }
        recyclerView?.setAdapter(universityAdapter)
        fetchUniversityData()
    }

    private fun fetchUniversityData() {
        // Use RetrofitClient to make the API call
        val universityApiService = RetrofitClient.universityApiService

        // Make the API call using a Coroutine (you need to import kotlinx.coroutines)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = universityApiService.getUniversities().execute()

                if (response.isSuccessful) {
                    val universityListResponse = response.body()
                    Log.v("TAG", universityListResponse.toString());

                    // Check if the response body is not null
                    if (universityListResponse != null) {
                        // Extract the universities from the response
                        val universities = universityListResponse
                        // Update the UI with the fetched data
                        withContext(Dispatchers.Main) {
                            universityAdapter?.updateData(universities)
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

    private fun openWebView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}