package com.example.duckapp.data

import retrofit2.http.GET

interface DuckApi {
    @GET("random")
    suspend fun getDuck() : Duck

    companion object{
        const val BASE_URL = "https://random-d.uk/api/"
    }
}