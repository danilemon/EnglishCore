import com.example.englishcoreappk.Retrofit.RetrofitClient

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

    fun UpdateStudentData(){

    }
}

