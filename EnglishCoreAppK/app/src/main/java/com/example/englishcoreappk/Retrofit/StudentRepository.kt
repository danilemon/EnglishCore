import android.net.Uri
import com.example.englishcoreappk.Retrofit.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object StudentRepository {
    private val api = RetrofitClient.instance.create(StudentService::class.java)

    fun GetStudentDataRequest(studentID: String, callback: (StudentData?) -> Unit) {
        val request = GetStudentDataRequest(StudentDocId = studentID)
        api.getStudentData(request).enqueue(object : Callback<StudentData> {
            override fun onResponse(call: Call<StudentData>, response: Response<StudentData>) {
                if (response.isSuccessful) {
                    val studentData = response.body()
                    callback(studentData)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<StudentData>, t: Throwable) {
                callback(null)
            }
        })
    }



    fun UpdateStudentData(studentDocID: String, newAddress: String, newPhone: String, callback: (Boolean) -> Unit) {
        val updatedFields = mapOf(
            "address" to newAddress,
            "Phone" to newPhone
        )

        api.updateStudentData(studentDocID, updatedFields).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    callback(true) // Éxito
                } else {
                    callback(false) // Fallo
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback(false)
            }
        })
    }

    fun GetStudentReminders(studentDocID: String, callback: (List<StudentReminders>?) -> Unit) {
        // Construir el cuerpo de la solicitud
        val request = GetStudentDataRequest(StudentDocId = studentDocID)

        // Llamar al endpoint de la API
        api.getStudentReminders(request).enqueue(object : Callback<List<StudentReminders>> {
            override fun onResponse(
                call: Call<List<StudentReminders>>,
                response: Response<List<StudentReminders>>
            ) {
                if (response.isSuccessful) {
                    // Devolver la lista de recordatorios
                    val remindersList = response.body()
                    callback(remindersList)
                } else {
                    // Devolver null en caso de error
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<StudentReminders>>, t: Throwable) {
                // Devolver null si falla la solicitud
                callback(null)
            }
        })
    }

    fun GetStudentTickets(studentDocID: String, callback: (List<GetStudentTickets>?) -> Unit) {
        // Construir el cuerpo de la solicitud
        val request = GetStudentDataRequest(StudentDocId = studentDocID)

        // Llamar al endpoint de la API
        api.getStudentTickets(request).enqueue(object : Callback<List<GetStudentTickets>> {
            override fun onResponse(
                call: Call<List<GetStudentTickets>>,
                response: Response<List<GetStudentTickets>>
            ) {
                if (response.isSuccessful) {
                    // Devolver la lista de recordatorios
                    val ticketsList = response.body()
                    callback(ticketsList)
                } else {
                    // Devolver null en caso de error
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<GetStudentTickets>>, t: Throwable) {
                // Devolver null si falla la solicitud
                callback(null)
            }
        })
    }



}
