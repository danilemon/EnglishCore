import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentService {
    @POST("/GetStudentData")
    fun getStudentData(@Body request: GetStudentDataRequest): Call<StudentData>
    @PUT("/UpdateStudentData/{studentDocID}")
    fun updateStudentData(
        @Path("studentDocID") studentDocID: String,
        @Body updatedFields: Map<String, String>
    ): Call<Unit>
    @POST("/GetStudentReminders")
    fun getStudentReminders(@Body request: GetStudentDataRequest): Call<List<StudentReminders>>

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
    val GroupID: String
)

data class StudentReminders(
    val ProfessorName: String,
    val Content: String,
    val Date: String
)
