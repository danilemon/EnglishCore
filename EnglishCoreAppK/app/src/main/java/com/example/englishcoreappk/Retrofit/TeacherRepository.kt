package com.example.englishcoreappk.Retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object TeacherRepository {
    private val api = RetrofitClient.instance.create(TeacherService::class.java)
    fun GetGroupsRequest(callback: (List<Groups>) -> Unit){
        val Usr=GetGroupsRequest(1)
        api.getGroups(Usr).enqueue(object : Callback<List<Groups>> {
            override fun onResponse(call: Call<List<Groups>>, response: Response<List<Groups>>) {
                var Groups=response.body()!!
                callback(Groups)
            }

            override fun onFailure(call: Call<List<Groups>>, t: Throwable) {
                callback(emptyList())
            }

        })

    }
}