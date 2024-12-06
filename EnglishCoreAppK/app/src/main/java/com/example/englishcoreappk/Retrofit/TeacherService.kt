package com.example.englishcoreappk.Retrofit

import kotlinx.parcelize.Parcelize
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

import kotlinx.serialization.Serializable


interface TeacherService {
        @POST("/GetGroups")
        fun getGroups(@Body request: GroupsRequest): Call<MutableList<Groups>>
        @POST("/GetStudents")
        fun getGroupsStudents(@Body request: GroupsRequest): Call<List<StudentPreview>>
        @POST("/GetStudentInfo")
        fun getStudentInfo(@Body request: GroupsRequest): Call<StudentInfo>
}

data class GroupsRequest(
    val ID: String
)


@Serializable
data class Groups(
    val ID: String? = null,
    val Days: String? = null,
    val Hours: String? = null,
    val Level: Int? = null,
    val StartDate: String? = null,
    val TeacherID: Int? = null
)
@Stable
data class StudentPreview(
    val ID: String,
    val Name: String
)
data class StudentInfo(
    val ID: String,
    val Name: String,
    val Cellphone: String,
    val Adrress:String
)