package com.example.englishcoreappk.Retrofit


import androidx.compose.runtime.Stable
import retrofit2.Call
import retrofit2.http.Body
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import kotlinx.serialization.Serializable
import retrofit2.http.POST
import java.lang.reflect.Type

interface ActIvityService{

    @POST("/GetActivity")
    fun GetActivity(@Body request: ActivityRequest): Call<Activity>

    @POST("/GetGroupActivities")
    fun GetGroupActs(@Body request: List<ActivityRequest>): Call<MutableList<List<Units>>>

    @POST("/GetGroupExams")
    fun GetGroupExams(@Body request: ActivityRequest): Call<List<ActivityPreview>>

    
}
data class ActivityRequest(
    val ID: String
)

data class ActivityPreview(
    var Name: String,
    var ID: String
)
data class Units(
    var ID: String,
    var Name: String,
    var Activities: List<ActivityPreview>
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


