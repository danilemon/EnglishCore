package com.example.englishcoreappk.Retrofit

import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ActivityRepository {
    private val api = RetrofitClient.instance.create(ActIvityService::class.java)
    fun GetActivityInfo(ActID: String, GroupID: String, HasAnswers:(String)->Unit, callback: (Activity)-> Unit){
        val ActRqst=GetActivity(GroupID,ActID, UserData.User)
        api.GetActivity(ActRqst).enqueue(object:Callback<Activity>{
            override fun onResponse(call: Call<Activity>, response: Response<Activity>) {
                if(response.isSuccessful){
                    val Act=response.body()!!
                    val ActQuestions = Act.Questions
                    val NQuestions: MutableList<Question> = mutableListOf()
                    ActQuestions.forEach{i->
                        when(i){
                            is OpenQuestion->{
                                var OpQ = i as OpenQuestion
                                var q: OpenQuestion= OpenQuestion(Type = OpQ.Type, Question = OpQ.Question, HelpText = OpQ.HelpText, Img = OpQ.Img, Answer = OpQ.Answer)
                                NQuestions.add(q)
                            }
                            is ClosedQuestion->{
                                var ClQ = i as ClosedQuestion
                                var q: ClosedQuestion= ClosedQuestion(Type = ClQ.Type, Question = ClQ.Question, HelpText = ClQ.HelpText, Img = ClQ.Img, Options = ClQ.Options, Answer = ClQ.Answer)
                                NQuestions.add(q)
                            }
                            is CompleteText ->{
                                var CTQ= i as CompleteText
                                var q: CompleteText= CompleteText(Type = CTQ.Type, Question = CTQ.Question, HelpText = CTQ.HelpText, Img = CTQ.Img, Text = CTQ.Text, Options = CTQ.Options, Answers = CTQ.Answers, MultipleSets=CTQ.MultipleSets ,NoRep = CTQ.NoRep)
                                NQuestions.add(q)
                            }
                        }
                    }
                    var FinalAct= Activity(ID =Act.ID, Name = Act.Name, Level = Act.Level, Topic = Act.Topic, NQuestions)
                    callback(FinalAct)
                }else{
                    HasAnswers("La actividad ya fue respondida")
                }
            }

            override fun onFailure(call: Call<Activity>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun GetAnsweredActivity(GroupID: String,ActivityID: String,callback:(AnsweredActivity)->Unit){
        var Request=GetActivityAnwers(GroupID,ActivityID)
        api.GetAnsweredActivity(Request).enqueue(object:  Callback<AnsweredActivity>{
            override fun onResponse(
                call: Call<AnsweredActivity?>,
                response: Response<AnsweredActivity?>
            ) {
                var R=response.body()!!
                callback(response.body()!!)
            }

            override fun onFailure(
                call: Call<AnsweredActivity?>,
                t: Throwable
            ) {
                println("Failed to fetch data from server")
                t.printStackTrace()
            }

        })

    }

    fun GetExam(ActID: String, GroupID: String, HasAnswers:(String)->Unit, callback: (Activity)-> Unit){
        val ActRqst=GetActivity(GroupID,ActID, UserData.User)
        api.GetExam(ActRqst).enqueue(object:Callback<Activity>{
            override fun onResponse(call: Call<Activity>, response: Response<Activity>) {
                if(response.isSuccessful){
                    val Act=response.body()!!
                    val ActQuestions = Act.Questions
                    val NQuestions: MutableList<Question> = mutableListOf()
                    ActQuestions.forEach{i->
                        when(i){
                            is OpenQuestion->{
                                var OpQ = i as OpenQuestion
                                var q: OpenQuestion= OpenQuestion(Type = OpQ.Type, Question = OpQ.Question, HelpText = OpQ.HelpText, Img = OpQ.Img, Answer = OpQ.Answer)
                                NQuestions.add(q)
                            }
                            is ClosedQuestion->{
                                var ClQ = i as ClosedQuestion
                                var q: ClosedQuestion= ClosedQuestion(Type = ClQ.Type, Question = ClQ.Question, HelpText = ClQ.HelpText, Img = ClQ.Img, Options = ClQ.Options, Answer = ClQ.Answer, TrueFalse =ClQ.TrueFalse )
                                NQuestions.add(q)
                            }
                            is CompleteText ->{
                                var CTQ= i as CompleteText
                                var q: CompleteText= CompleteText(Type = CTQ.Type, Question = CTQ.Question, HelpText = CTQ.HelpText, Img = CTQ.Img, Text = CTQ.Text, Options = CTQ.Options, Answers = CTQ.Answers, MultipleSets=CTQ.MultipleSets,NoRep = CTQ.NoRep)
                                NQuestions.add(q)
                            }
                        }
                    }
                    var FinalAct= Activity(ID =Act.ID, Name = Act.Name, Level = Act.Level, Topic = Act.Topic, NQuestions)
                    callback(FinalAct)
                }else{
                    HasAnswers("La actividad ya fue respondida")
                }
            }

            override fun onFailure(call: Call<Activity>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    //Obtener datos para asignar
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

    //Asignar
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


    //Obtener asignados
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

    //Subir Calificaciones
    fun UploadAnswers(Answers:UploadAnswers){
        api.UploadActivityAnswers(Answers).enqueue(object: Callback<String>{
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {

            }

            override fun onFailure(call: Call<String?>, t: Throwable) {

            }

        })
    }
}