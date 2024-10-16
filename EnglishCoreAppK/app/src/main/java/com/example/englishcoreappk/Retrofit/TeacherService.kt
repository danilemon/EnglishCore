package com.example.englishcoreappk.Retrofit

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

interface TeacherService {
        @POST("/GetGroups")
        fun getGroups(@Body teacher: GetGroupsRequest): Call<List<Groups>>
}

data class GetGroupsRequest(
    val ID: Int
)

// Definir el modelo que recibirás de la respuesta
data class Groups(
    val ID: Int,
    val Days: String,
    val Hours: String,
    val Level: Int,
    val StartDate: String,
    val TeacherID: Int
)