package com.example.englishcoreappk.Retrofit
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface ApiService {

    @POST("/Login")  // Cambia esto si la ruta de tu API es diferente
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}

// Clase para manejar la respuesta del servidor
data class LoginResponse(
    val success: Boolean,  // Esta propiedad indica si el login fue exitoso o no
    val message: String    // Este es el mensaje que el servidor puede devolver
)
data class LoginRequest(
    val username: String,
    val password: String
)




