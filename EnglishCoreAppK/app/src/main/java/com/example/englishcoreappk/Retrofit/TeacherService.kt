package com.example.englishcoreappk.Retrofit

import kotlinx.parcelize.Parcelize
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import android.os.Parcelable
import kotlinx.serialization.Serializable


interface TeacherService {
        @POST("/GetGroups")
        fun getGroups(@Body request: GroupsRequest): Call<List<Groups>>
        @POST("/GetStudents")
        fun getGroupsStudents(@Body request: GroupsRequest): Call<List<StudentPreview>>
        @POST("/GetStudentInfo")
        fun getStudentInfo(@Body request: GroupsRequest): Call<StudentInfo>
}

data class GroupsRequest(
    val ID: Int
)

// Definir el modelo que recibirás de la respuesta
@Serializable
data class Groups(
    val ID: Int? = null,
    val Days: String? = null,
    val Hours: String? = null,
    val Level: Int? = null,
    val StartDate: String? = null,
    val TeacherID: Int? = null
)

data class StudentPreview(
    val ID: Int,
    val Name: String
)
data class StudentInfo(
    val ID: Int,
    val Name: String,
    val Cellphone: String,
    val Adrress:String
)