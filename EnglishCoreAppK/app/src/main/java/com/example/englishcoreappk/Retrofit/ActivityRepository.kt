package com.example.englishcoreappk.Retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ActivityRepository {
    private val api = RetrofitClient.instance.create(ActIvityService::class.java)
    fun GetActivityInfo(ID:Int,callback: (Activity)-> Unit){
        val ActRqst=ActivityRequest(ID)
        api.GetActivity(ActRqst).enqueue(object:Callback<Activity>{
            override fun onResponse(call: Call<Activity>, response: Response<Activity>) {
                val ActivityResponse = response.body()!!
                callback(ActivityResponse)
            }

            override fun onFailure(call: Call<Activity>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}