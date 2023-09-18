package com.task.vahan
import retrofit2.Call
import retrofit2.http.GET

interface UniversityApiService {
    @GET("search")
    fun getUniversities(): Call<List<University>>
}
