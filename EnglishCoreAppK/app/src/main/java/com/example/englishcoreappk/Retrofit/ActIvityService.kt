package com.example.englishcoreappk.Retrofit


import android.R
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.StableMarker
import retrofit2.Call
import retrofit2.http.Body
import kotlinx.serialization.Serializable
import retrofit2.http.POST
interface ActIvityService{
    //Obtener Actividades
    @POST("/GetActivity")
    fun GetActivity(@Body request: GetActivity): Call<Activity>

    @POST("/GetAnsweredActivity")
    fun GetAnsweredActivity(@Body request: GetActivityAnwers):Call<AnsweredActivity>

    //Obtener Actividades
    @POST("/GetExam")
    fun GetExam(@Body request: GetActivity): Call<Activity>
    //Obtener datos para asignar
    @POST("/GetGroupActivities")
    fun GetGroupActs(@Body request: List<ActivityRequest>): Call<MutableList<List<Units>>>

    @POST("/GetGroupExams")
    fun GetGroupExams(@Body request: List<ActivityRequest>): Call<MutableList<List<ActivityPreview>>>

    @POST("/GetPractices")
    fun GetPractices(@Body request: ActivityRequest): Call<PracticesPck>

    //Asignar
    @POST("/AsiggnActivity")
    fun AssignActivity(@Body request: AsignActivityPck): Call<String>

    @POST("/AsiggnExam")
    fun AssignExam(@Body request: AsignExamPck): Call<String>

    @POST("/AsiggnPractice")
    fun AssignPractice(@Body request: AsignPracticePck): Call<String>

    //Obtener ya asignados
    @POST("/GetAssignedActivities")
    fun GetAssignedActivities(@Body request: ActivityRequest): Call<List<UnitViews>>

    @POST("/GetAssignedExams")
    fun GetAssignedExams(@Body request: ActivityRequest): Call<List<AsignedView>>

    @POST("/GetAssignedPractices")
    fun GetAssignedPractices(@Body request: ActivityRequest): Call<List<AsignedView>>

    //Subir Respuestas
    @POST("/UploadActivityAnswers")
    fun UploadActivityAnswers(@Body request: UploadAnswers): Call<String>

}

//Requests
data class ActivityRequest(
    val ID: String
)

data class GetActivityAnwers(
    val GroupID: String,
    val ActID: String
)

data class GetActivity(
    val GroupID: String,
    val ActID: String,
    val UsrID: String
)

data class UploadAnswers(
    val GroupID: String,
    val ActID: String,
    val UsrID: String,
    val Score: Int,
    val Answers: List<ActivityAnswer>
)


data class ActivityPreview(
    var Name: String,
    var ID: String,
)

//Views de actividades asignadas
data class UnitViews(
    val Name: String,
    val ID: String,
    val Acts: List<AsignedView>
)
data class AsignedView(
    var HasAnswers: Boolean,
    var Act: ActivityPreview
)


//PAaquetes oara asignar actividades
data class PracticesPck(
    var Students:List<StudentPreview>,
    var Practices:List<ActivityPreview>
)
data class AsignActivityPck(
    var GroupID: String,
    var UnitID: String="",
    var ActivityID: String
)
data class AsignPracticePck(
    var TeacherID: String,
    var StudentID: String,
    var PracticeID: String
)
data class AsignExamPck(
    var GroupID: String,
    var ExamID: String,
    var Minutes: Int,
    var Tries: Int,
    var Date: String
)
data class Units(
    var ID: String,
    var Name: String,
    var Activities: List<ActivityPreview>
)

//Actividades
@Immutable
data class Activity(
        val ID:String,
        val Name:String,
        val Level:Int,
        val Topic:String,
        val Questions:List<Question>
        )
@Serializable
open class Question(

    open val questionQ: String,
    open val helpTextQ: String,
    open val imgQ: String,
    open val typeq: Int

)

@Serializable
data class OpenQuestion(
    val Type: Int,
    val Question: String,
    val HelpText: String="",
    val Img: String="",
    val Answer: String
) : Question(
    questionQ = Question,
    helpTextQ = HelpText,
    imgQ = Img,
    typeq = Type
)

@Serializable
data class ClosedQuestion(
    val Type: Int,
    val Question: String,
    val HelpText: String="",
    val Img: String="",
    val Options: List<String> = emptyList<String>(),
    val TrueFalse: Boolean=false,
    val Answer: Int
) : Question(
    questionQ = Question,   // Asignar explícitamente a questionQ
    helpTextQ = HelpText,   // Asignar explícitamente a helpTextQ
    imgQ = Img,
    typeq = Type
    // Asignar explícitamente a imgQ
)

@Serializable
data class Set(
    var Sett: List<String>
)

@Serializable
data class CompleteText(
    val Type: Int,
    val Question: String,
    val HelpText: String,
    val Img: String,
    val Text: String,
    val Options: List<String>,
    val Answers: List<String>,
    val MultipleSets: MutableList<Int>,
    val NoRep: Boolean
) : Question(
    questionQ = Question,   // Asignar explícitamente a questionQ
    helpTextQ = HelpText,   // Asignar explícitamente a helpTextQ
    imgQ = Img,
    typeq = Type              // Asignar explícitamente a imgQ
)


//Actividades contestadas
@Stable
data class ActivityAnswer(
    val Type:Int,
    val Correct: Boolean,
    val value: Any=""
)



@Stable
data class StudentAnswers(
    val ID: String,
    val student: StudentPreview,
    val Answers: List<ActivityAnswer>
)

@Immutable
data class Answers(
    val List:List<StudentAnswers>
)
@Stable
data class AnsweredActivity(
    val Act: Activity,
    val StudentsAnswers: List<StudentAnswers>
)


