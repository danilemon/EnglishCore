package com.example.englishcoreappk.Retrofit
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.75:5000"  // Cambia esto por la URL de tu API
    val gson = GsonBuilder()
        .registerTypeAdapter(Question::class.java, QuestionDeserializer())
        .create()

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))  // Usa Gson si tus respuestas están en formato JSON
            .build()
    }
}

class QuestionDeserializer : JsonDeserializer<Question> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Question {
        val jsonObject = json.asJsonObject
        val type = jsonObject["Type"].asInt

        return when (type) {
            1 -> context.deserialize(json, OpenQuestion::class.java)
            2 -> context.deserialize(json, ClosedQuestion::class.java)
            3 -> context.deserialize(json, CompleteText::class.java)
            else -> throw JsonParseException("Unknown type: $type")
        }
    }
}