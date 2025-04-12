package com.example.bookxperttest.service

import com.example.bookxperttest.models.ApiObject
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    suspend fun fetchObjects(): Response<List<ApiObject>>
}