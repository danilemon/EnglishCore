package com.example.englishcoreappk.Retrofit


import android.R
import androidx.compose.runtime.Stable
import retrofit2.Call
import retrofit2.http.Body
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kotlinx.serialization.Serializable
import retrofit2.http.POST
interface ActIvityService{

    @POST("/GetActivity")
    fun GetActivity(@Body request: ActivityRequest): Call<Activity>

    @POST("/GetGroupActivities")
    fun GetGroupActs(@Body request: List<ActivityRequest>): Call<MutableList<List<Units>>>

    @POST("/GetGroupExams")
    fun GetGroupExams(@Body request: List<ActivityRequest>): Call<MutableList<List<ActivityPreview>>>

    @POST("/GetPractices")
    fun GetPractices(@Body request: ActivityRequest): Call<PracticesPck>

    @POST("/AsiggnActivity")
    fun AssignActivity(@Body request: AsignActivityPck): Call<String>

    @POST("/AsiggnExam")
    fun AssignExam(@Body request: AsignExamPck): Call<String>

    @POST("/AsiggnPractice")
    fun AssignPractice(@Body request: AsignPracticePck): Call<String>

    @POST("/GetAssignedActivities")
    fun GetAssignedActivities(@Body request: ActivityRequest): Call<List<AsignedView>>

    @POST("/GetAssignedExams")
    fun GetAssignedExams(@Body request: ActivityRequest): Call<List<AsignedView>>

    @POST("/GetAssignedExams")
    fun GetAssignedPractices(@Body request: ActivityRequest): Call<List<AsignedView>>
}
data class ActivityRequest(
    val ID: String
)
data class ActivityPreview(
    var Name: String,
    var ID: String
)
data class AsignedView(
    var HasAnswers: Boolean,
    var Act: ActivityPreview
)
data class Units(
    var ID: String,
    var Name: String,
    var Activities: List<ActivityPreview>
)
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
@Stable
data class Activity(
        val ID:String,
        val Name:String,
        val Level:Int,
        val Topic:String,
        val Questions:List<Question>
        )
@Serializable
open class Question(
    open val typeQ: Int,
    open val questionQ: String,
    open val helpTextQ: String,
    open val imgQ: String
)

@Serializable
data class OpenQuestion(
    val type: Int,
    val question: String,
    val helpText: String,
    val img: String,
    val answer: String
) : Question(type, question, helpText, img)

@Serializable
data class ClosedQuestion(
    val type: Int,
    val question: String,
    val helpText: String,
    val img: String,
    val options: List<String>,
    val answer: String
) : Question(type, question, helpText, img)

@Serializable
data class CompleteText(
    val type: Int,
    val question: String,
    val helpText: String,
    val img: String,
    val text: String,
    val options: List<String>,
    val answers: List<String>
) : Question(type, question, helpText, img)

@Stable
data class UserAnswer(
    var Correct: Boolean,
    var Answers: Any
)


