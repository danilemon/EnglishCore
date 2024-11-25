import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface StudentService {
    @POST("/GetStudentData")
    fun getStudentData(@Body request: GetStudentDataRequest): Call<StudentData>
}

data class GetStudentDataRequest(
    val StudentDocId: String
)

data class StudentData(
    val StudentID: Int,
    val Name: String,
    val LastName: String,
    val Phone: String,
    val Address: String,
    val Birthday: String,
    val Username: String,
    val GroupID: Int
)
