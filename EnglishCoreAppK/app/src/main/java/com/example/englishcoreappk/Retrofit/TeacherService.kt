package com.example.englishcoreappk.Retrofit

import kotlinx.parcelize.Parcelize
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import android.os.Parcelable


interface TeacherService {
        @POST("/GetGroups")
        fun getGroups(@Body request: GroupsRequest): Call<List<Groups>>
        @POST("/GetStudents")
        fun getGroupsStudents(@Body request: GroupsRequest): Call<List<StudentPreview>>
}

data class GroupsRequest(
    val ID: Int
)

// Definir el modelo que recibirás de la respuesta
@Parcelize
data class Groups(
    val ID: Int,
    val Days: String,
    val Hours: String,
    val Level: Int,
    val StartDate: String,
    val TeacherID: Int
): Parcelable

data class StudentPreview(
    val ID: Int,
    val Name: String
)