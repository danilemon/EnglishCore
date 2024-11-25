package com.example.englishcoreappk.Retrofit
import okhttp3.ResponseBody
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
    @POST("/Register")
    fun Register(@Body request: RegisterRequest): Call<GenericResponse>

}

// Clase para manejar la respuesta del servidor
data class LoginResponse(
    val success: Boolean,  // Esta propiedad indica si el login fue exitoso o no
    val message: String,    // Este es el mensaje que el servidor puede devolver
    val isStudent: Boolean,
    val userDocId: String
)


data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,//Usuario que se le enntego al momento de registrarse a la escuela
    val password: String,//contrasenia que asignara a su cuenta
    val adrees: String,//direccion
    val birthday: String,//fecha de nacimiento
    val phone: String//telefono de contacto
)

data class GenericResponse(
    val message:String
)




