package com.example.englishcoreappk.Retrofit

import okhttp3.Request
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

    fun GetGroupExms(Request: MutableList<ActivityRequest>, callback: (MutableList<List<ActivityPreview>>)-> Unit){
        api.GetGroupExams(Request).enqueue(object: Callback<MutableList<List<ActivityPreview>>>{
            override fun onResponse(
                call: Call<MutableList<List<ActivityPreview>>?>,
                response: Response<MutableList<List<ActivityPreview>>?>
            ) {
                var Acts=response.body()!!
                callback(Acts)
            }

            override fun onFailure(
                call: Call<MutableList<List<ActivityPreview>>?>,
                t: Throwable
            ) {
            }
        })
    }

    fun GetPractices(callback: (PracticesPck) -> Unit){
        var Ruquest= ActivityRequest(UserData.User)
        api.GetPractices(Ruquest).enqueue(object: Callback<PracticesPck>{
            override fun onResponse(
                call: Call<PracticesPck?>,
                response: Response<PracticesPck?>
            ) {
                var Practices=response.body()!!
                callback(Practices)
            }

            override fun onFailure(
                call: Call<PracticesPck?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }


        })

    }

    fun AsignActivity(GroupID: String,UnitID: String,ActivityID: String,callback:(String)-> Unit){
        var Data=AsignActivityPck(GroupID,UnitID,ActivityID)
        api.AssignActivity(Data).enqueue(object: Callback<String>{
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                print("A")
            }
        })
    }

    fun AsignExam(GroupID: String, ActivityID: String, Tries: Int, Min: Int, Date: String, callback:(String)-> Unit){
        var Data= AsignExamPck(GroupID,ActivityID,Min,Tries,Date)
        api.AssignExam(Data).enqueue(object: Callback<String>{
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                print("A")
            }
        })
    }

    fun AsignPractice(TeacherID: String, StudentID: String, PracticeID: String, callback:(String)-> Unit){
        var Data= AsignPracticePck(TeacherID,StudentID,PracticeID)
        api.AssignPractice(Data).enqueue(object: Callback<String>{
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                print("A")
            }
        })
    }

    fun GetActsAsigned(GroupID: String,callback: (List<UnitViews>) -> Unit){
        var Request=ActivityRequest(GroupID)
        api.GetAssignedActivities(Request).enqueue(object: Callback<List<UnitViews>> {
            override fun onResponse(
                call: Call<List<UnitViews>?>,
                response: Response<List<UnitViews>?>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(
                call: Call<List<UnitViews>?>,
                t: Throwable
            ) {
                callback(emptyList())
            }

        })

    }

    fun GetExamAsigned(GroupID: String,callback: (List<AsignedView>) -> Unit){
        var Request=ActivityRequest(GroupID)
        api.GetAssignedExams(Request).enqueue(object: Callback<List<AsignedView>> {
            override fun onResponse(
                call: Call<List<AsignedView>?>,
                response: Response<List<AsignedView>?>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(
                call: Call<List<AsignedView>?>,
                t: Throwable
            ) {
                callback(emptyList())
            }

        })

    }

    fun GetPracticesAsigned(GroupID: String,callback: (List<AsignedView>) -> Unit){
        var Request=ActivityRequest(GroupID)
        api.GetAssignedPractices(Request).enqueue(object: Callback<List<AsignedView>> {
            override fun onResponse(
                call: Call<List<AsignedView>?>,
                response: Response<List<AsignedView>?>
            ) {
                callback(response.body()!!)
            }

            override fun onFailure(
                call: Call<List<AsignedView>?>,
                t: Throwable
            ) {
                callback(emptyList())
            }

        })

    }
}