package com.example.englishcoreappk.Retrofit

import android.view.PixelCopy.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TeacherRepository {
    private val api = RetrofitClient.instance.create(TeacherService::class.java)
    fun GetGroupsRequest(ID: String, callback: (MutableList<Groups>) -> Unit){
        val Usr=GroupsRequest(ID)
        api.getGroups(Usr).enqueue(object : Callback<MutableList<Groups>> {
            override fun onResponse(call: Call<MutableList<Groups>>, response: Response<MutableList<Groups>>) {
                val Groups=response.body()!!
                callback(Groups)
            }

            override fun onFailure(call: Call<MutableList<Groups>>, t: Throwable) {
                callback(mutableListOf())
            }

        })
    }

    fun GetGroupStudets(GroupID: String, callback: (List<StudentPreview>) -> Unit){
        val Request =GroupsRequest(GroupID)
        api.getGroupsStudents(Request).enqueue(object: Callback<List<StudentPreview>>{
            override fun onResponse(
                call: Call<List<StudentPreview>>,
                response: Response<List<StudentPreview>>
            ) {
                val Students=response.body()!!
                callback(Students)
            }

            override fun onFailure(call: Call<List<StudentPreview>>, t: Throwable) {
                callback(emptyList())
            }

        })
    }

    fun GetStudentInfo(StudentId: String, callback: (StudentInfo)->Unit){
        val Request=GroupsRequest(StudentId)
        api.getStudentInfo(Request).enqueue(object: Callback<StudentInfo>{
            override fun onResponse(call: Call<StudentInfo>, response: Response<StudentInfo>) {
                val Student=response.body()!!
                callback(Student)
            }

            override fun onFailure(call: Call<StudentInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }

        } )
    }
}