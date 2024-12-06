package com.example.englishcoreappk.Students

import StudentData
import StudentReminders
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Retrofit.UserData
import com.example.englishcoreappk.Teachers.BottomNavigationBar
import com.example.englishcoreappk.Teachers.NavigationHost
import com.example.englishcoreappk.Teachers.ShowView
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class StudentsPending : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                PreviewPending()
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowStudentPending() {
    val context = LocalContext.current
    val userrrr = UserData.User
    var isLoadingProfile = remember { mutableStateOf(true) } // Estado para cargar
    var remindersList by remember { mutableStateOf<List<StudentReminders>?>(emptyList()) }
    var selectedReminder by remember { mutableStateOf<StudentReminders?>(null) }


    // Cargar datos del estudiante y recordatorios




    Box(modifier = Modifier.fillMaxSize().background(Color(0xffD9D9D9))) {


        LaunchedEffect(Unit) {
            StudentRepository.GetStudentReminders(studentDocID = userrrr) { reminders ->
                println("Reminders recibidos del servidor: $reminders")

                if (reminders == null || reminders.isEmpty()) {
                    println("No se encontraron recordatorios o los datos son nulos.")
                    remindersList = emptyList()
                } else {
                    // Filtrar y limpiar datos nulos
                    remindersList = reminders.mapNotNull { reminder ->
                        if (reminder.ProfessorName != null && reminder.Title != null && reminder.Date != null) {
                            reminder
                        } else {
                            println("Recordatorio inválido encontrado: $reminder")
                            null
                        }
                    }
                }
                isLoadingProfile.value = false
            }
        }

        if (isLoadingProfile.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {


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
                                .clickable {
                                    val intent = Intent(context, StudentsDashboard::class.java)
                                    context.startActivityWithAnimation(intent)
                                }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.profile_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .align(Alignment.TopStart)
                                .clickable {
                                    val intent = Intent(context, StudentsProfile::class.java)
                                    context.startActivityWithAnimation(intent)
                                }
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
                    )
                    {
                        Text(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 25.sp,
                            text = "Pendings",
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                        )
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .background(Color(0xff2e4053))
                                .fillMaxSize()
                        ) {
                            if (remindersList.isNullOrEmpty()) {
                                item {
                                    Text(
                                        text = "No pending reminders.",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }
                            } else {
                                remindersList?.forEach { reminder ->
                                    item {
                                        PendingCards(
                                            professorname = reminder.ProfessorName,
                                            title = reminder.Title,
                                            date = reminder.Date,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth()
                                                .clickable {
                                                    // Abre el diálogo con los detalles del recordatorio
                                                    selectedReminder = reminder
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
            // Mostrar el diálogo cuando se selecciona un recordatorio
            selectedReminder?.let { reminder ->
                ReminderDetailsDialog(reminder = reminder) {
                    // Cierra el diálogo
                    selectedReminder = null
                }
            }
        }
    }

}

@Composable
fun PendingCards(professorname: String, title: String, date: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable { /* Acción cuando se clickea la tarjeta */ }
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp)
        ) {
            Text(text = professorname, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = title, color = Color.Gray)
            Text(text = date, color = Color.LightGray, fontSize = 12.sp)
        }
    }
}
@Composable
fun ReminderDetailsDialog(reminder: StudentReminders, onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Reminder Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        },
        text = {
            Column {
                Text(text = "Professor: ${reminder.ProfessorName}", fontSize = 16.sp)
                Text(text = "Title: ${reminder.Title}", fontSize = 16.sp)
                Text(text = "Date: ${reminder.Date}", fontSize = 16.sp) }
        },
        confirmButton = {
            androidx.compose.material3.Button(onClick = { onDismiss() }) {
                Text(text = "Close")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPending() {
    EnglishCoreAppKTheme {
        ShowStudentPending()
    }
}

