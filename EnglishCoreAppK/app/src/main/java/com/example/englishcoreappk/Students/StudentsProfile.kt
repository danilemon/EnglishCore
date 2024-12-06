package com.example.englishcoreappk.Students

import StudentData
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.material3.OutlinedTextField // Import necesario para los campos de texto
import androidx.compose.ui.text.input.TextFieldValue // Import necesario para el estado del campo de texto

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Retrofit.Groups
import StudentRepository
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalContext
import com.example.englishcoreappk.Retrofit.TeacherRepository
import com.example.englishcoreappk.Retrofit.UserData
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme


class StudentsProfile: ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowProfileView()
            }
        }
    }
}

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ShowProfileView() {
        val context = LocalContext.current
        var isLoadingProfile = remember { mutableStateOf(true) } // Estado para cargar
        var userProgress by remember { mutableStateOf(0.5) }
        val studentDataState = remember { mutableStateOf<StudentData?>(null) }
        var isEditing by remember { mutableStateOf(false) } // Estado para el modo de edición
        var phoneText by remember { mutableStateOf(TextFieldValue("")) }
        var addressText by remember { mutableStateOf(TextFieldValue("")) }
        val userrrr = UserData.User

        Box(modifier = Modifier.fillMaxSize().background(Color(0xffD9D9D9))) {

            LaunchedEffect(Unit) {
                StudentRepository.GetStudentDataRequest(studentID = userrrr) { studentData ->
                    studentDataState.value = studentData
                    isLoadingProfile.value = false
                }
            }

            if (isLoadingProfile.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else{
                studentDataState.value?.let { student ->

                    Scaffold(
                        topBar = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp) // altura de la barra superior
                                    .background(Color(0xffD9D9D9))
                                    .padding(top = 5.dp)

                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.englishcorelogo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(50.dp) // Tamaño de la imagen
                                        .clip(CircleShape) // Aplica la forma circular
                                        .align(Alignment.TopCenter)  // Alinea la imagen en el centro del Box
                                        .clickable { val intent = Intent(context, StudentsDashboard::class.java)
                                            context.startActivity(intent) }
                                )

                            }
                        },


                        ) { contentPadding ->
                        Box(
                            modifier = Modifier.fillMaxSize().padding(contentPadding)
                                .background(Color(0xffD9D9D9))
                        )
                        {
                            Column(
                                modifier = Modifier.clip(
                                    RoundedCornerShape(
                                        topStart = 40.dp,
                                        topEnd = 40.dp
                                    )
                                ).fillMaxSize().background(Color.White)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().align(Alignment.Start)
                                        .height(100.dp)
                                )
                                {
                                    Box(
                                        modifier = Modifier.align(Alignment.CenterStart)
                                            .height(80.dp).width(200.dp).padding(start = 30.dp)
                                    )
                                    {
                                        Text(
                                            text = "${student.Name} ${student.LastName}", // Añade un espacio entre Name y LastName
                                            fontSize = 25.sp,
                                            fontWeight = FontWeight(900),
                                            color = Color.Black,
                                            textAlign = TextAlign.Justify,
                                            softWrap = true, // Habilita el ajuste de línea
                                            modifier = Modifier.fillMaxWidth() // Asegura que el texto ocupe todo el ancho disponible
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                            .fillMaxHeight().width(200.dp).padding(30.dp)
                                    )
                                    {
                                        Text(
                                            text = "${(userProgress * 100).toInt()}%",
                                            fontSize = 18.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight(600),
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                                .padding(end = 10.dp)
                                        )
                                        CircularProgressIndicator(
                                            trackColor = Color.LightGray,
                                            color = Color(0xff2e4053),
                                            progress = userProgress.toFloat(),
                                            modifier = Modifier.width(70.dp).height(70.dp)
                                                .padding(end = 30.dp)
                                        )

                                    }


                                }
                                Column(
                                    modifier = Modifier.fillMaxWidth().height(if (isEditing) 350.dp else 250.dp)
                                        .background(Color(0xff276127)).padding(16.dp)

                                )
                                {
                                    CompositionLocalProvider(LocalContentColor provides Color.White) {
                                        Text(
                                            text = "INFORMACIÓN",
                                            fontSize = 18.sp,
                                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                                .padding(top = 20.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = "Registro: ${student.StudentID}",
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        Text(
                                            text = "Grupo: ${student.GroupID} ",
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                        if (isEditing) {
                                            // Campos de texto para edición
                                            OutlinedTextField(
                                                value = phoneText,
                                                onValueChange = { phoneText = it },
                                                label = { Text("Teléfono") },
                                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                                    containerColor = Color.White,

                                                ),
                                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                            )
                                            OutlinedTextField(
                                                value = addressText,
                                                onValueChange = { addressText = it },
                                                label = { Text("Dirección") },
                                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                                    containerColor = Color.White,

                                                ),
                                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                            )
                                        } else {
                                            // Textos normales si no está en edición
                                            Text(
                                                text = "Teléfono: ${student.Phone}",
                                                modifier = Modifier.padding(start = 8.dp)
                                            )
                                            Text(
                                                text = "Dirección: ${student.Address}",
                                                modifier = Modifier.padding(start = 8.dp)
                                            )
                                        }
                                        Text(
                                            text = "Fecha de nacimiento: ${student.Birthday}",
                                            modifier = Modifier.padding(start = 8.dp)
                                        )

                                    }
                                }
                                Row (modifier=Modifier.height(40.dp).fillMaxWidth().align(Alignment.CenterHorizontally).padding(start = 20.dp)){
                                    Text(
                                    if (isEditing) "CANCELAR" else "EDITAR DATOS",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight(1000),
                                    color = Color.Black,
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                        .padding(top = 16.dp, end = 100.dp)
                                        .clickable { isEditing = !isEditing } // Cambia el estado al hacer clic
                                    )
                                    if (isEditing) {
                                        Text(
                                            text = "GUARDAR DATOS",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xff196f3d),
                                            textDecoration = TextDecoration.Underline,
                                            modifier = Modifier.align(Alignment.CenterVertically).padding(top = 16.dp)
                                                .clickable {
                                                    val updatedPhone = phoneText.text
                                                    val updatedAddress = addressText.text
                                                    StudentRepository.UpdateStudentData(
                                                        studentDocID = UserData.User, // Asegúrate de tener este campo en el modelo
                                                        newAddress = updatedAddress,
                                                        newPhone = updatedPhone
                                                    ) { success ->
                                                        var text = ""
                                                        val duration = Toast.LENGTH_SHORT
                                                        if (success) {
                                                            isEditing = false
                                                            val intent = (context as Activity).intent
                                                            context.finish()
                                                            text = "Your data has changed successfully"
                                                            val toast = Toast.makeText(context, text, duration) // in Activity

                                                            toast.show()

                                                            context.startActivity(intent)

                                                        } else {
                                                            text = "An error happened during the data change"
                                                            val toast = Toast.makeText(context, text, duration) // in Activity

                                                            toast.show()
                                                        }
                                                    }
                                                }
                                        )
                                    }


                                }

                                Image(
                                    painter = painterResource(id = R.drawable.bigbang_img),
                                    contentDescription = null,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                        .padding(top = 40.dp)
                                )


                            }
                        }


                    }
                }
            } ?:run{
                Text(text = "Hubo un problema al cargar los datos", color= Color.Red, modifier = Modifier.align(Alignment.Center))
            }


        }




    }


    @Preview(showBackground = true)
    @Composable
    fun PreviewProfile() {
        EnglishCoreAppKTheme {
            ShowProfileView()
        }
    }


