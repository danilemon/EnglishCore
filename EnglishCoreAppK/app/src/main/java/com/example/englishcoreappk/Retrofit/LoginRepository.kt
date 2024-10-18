package com.example.englishcoreappk.Retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    private val api = RetrofitClient.instance.create(ApiService::class.java)

    fun login(username: String, password: String, callback: (Boolean, String?,Boolean) -> Unit) {
        val loginRequest = LoginRequest(username, password)  // Usa LoginRequest

        api.login(loginRequest).enqueue(object : Callback<LoginResponse> {  // Usa LoginResponse
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    // Login exitoso
                    callback(true, null, response.body()!!.isStudent)
                } else {
                    // Error de la API
                    callback(false, response.body()?.message ?: "Credenciales inválidas",false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Error en la solicitud (problema de red, etc.)
                callback(false, t.message,false)
            }
        })
    }

    fun Register(username: String, password: String,Date:String,Adrees:String,Phone:String, callback: (Boolean, String?) -> Unit){
        val RegisterRequest = RegisterRequest(username,password,Adrees,Date,Phone)
        api.Register(RegisterRequest).enqueue(object : Callback<GenericResponse>{
            override fun onResponse(
                call: Call<GenericResponse>,
                response: Response<GenericResponse>
            ) {
                callback(response.isSuccessful,response.message())
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                callback(false, t.message)
            }




        })

    }
}
