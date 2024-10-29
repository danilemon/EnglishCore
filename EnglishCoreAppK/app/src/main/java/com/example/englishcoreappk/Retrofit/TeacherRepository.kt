package com.example.englishcoreappk.Retrofit

import android.view.PixelCopy.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TeacherRepository {
    private val api = RetrofitClient.instance.create(TeacherService::class.java)
    fun GetGroupsRequest(callback: (List<Groups>) -> Unit){
        val Usr=GroupsRequest(1)
        api.getGroups(Usr).enqueue(object : Callback<List<Groups>> {
            override fun onResponse(call: Call<List<Groups>>, response: Response<List<Groups>>) {
                val Groups=response.body()!!
                callback(Groups)
            }

            override fun onFailure(call: Call<List<Groups>>, t: Throwable) {
                callback(emptyList())
            }

        })
    }

    fun GetGroupStudets(GroupID:Int,callback: (List<StudentPreview>) -> Unit){
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
}