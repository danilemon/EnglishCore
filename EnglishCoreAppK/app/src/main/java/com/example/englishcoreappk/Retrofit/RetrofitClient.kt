package com.example.englishcoreappk.Retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.9:5000"  // Cambia esto por la URL de tu API

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Usa Gson si tus respuestas están en formato JSON
            .build()
    }
}