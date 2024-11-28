package com.example.englishcoreappk.Retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ActivityRepository {
    private val api = RetrofitClient.instance.create(ActIvityService::class.java)
    fun GetActivityInfo(ID: String, callback: (Activity)-> Unit){
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

    fun GetGroupActss(Request: MutableList<ActivityRequest>, callback: (MutableList<List<Units>>)-> Unit){
        api.GetGroupActs(Request).enqueue(object: Callback<MutableList<List<Units>>>{
            override fun onResponse(
                call: Call<MutableList<List<Units>>>,
                response: Response<MutableList<List<Units>>>
            ) {
                val Units = response.body()!!
                callback(Units)
            }

            override fun onFailure(
                call: Call<MutableList<List<Units>>>,
                t: Throwable
            ) {

            }

        })
    }

    fun GetGroupExms(ID: String, callback: (List<ActivityPreview>)-> Unit){
        val ActRqst=ActivityRequest(ID)
        api.GetGroupExams(ActRqst).enqueue(object: Callback<List<ActivityPreview>>{
            override fun onResponse(
                call: Call<List<ActivityPreview>?>,
                response: Response<List<ActivityPreview>?>
            ) {
                var Acts=response.body()!!
                callback(Acts)
            }

            override fun onFailure(
                call: Call<List<ActivityPreview>?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }
}