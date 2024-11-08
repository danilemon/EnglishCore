package com.example.englishcoreappk.Students

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Teachers.BottomNavigationBar
import com.example.englishcoreappk.Teachers.NavigationHost
import com.example.englishcoreappk.Teachers.ShowView
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class StudentsDashboard : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowStudentView()
            }
        }
    }
}

fun Context.startActivityWithAnimation(intent: Intent) {
    startActivity(intent)
    if (this is Activity) {
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_down)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowStudentView() {
    val context = LocalContext.current

    val navController = rememberNavController()
    var ExitDialog by remember { mutableStateOf(true) }

    BackHandler(enabled = ExitDialog) {
        ExitDialog = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopEnd)
                            .clickable { ExitDialog = !ExitDialog }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopStart)
                            .clickable { val intent = Intent(context, StudentsProfile::class.java)
                                context.startActivityWithAnimation(intent)}
                    )
                }
            },


        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Imagen de fondo principal


                // Tarjetas de Practicar, Pendientes y Calificaciones
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp) // Añade un pequeño padding inferior si es necesario
                ) {
                    item{
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp).padding(top= 50.dp) // Ajusta la altura de la imagen
                        ) {
                            // Imagen de fondo principal
                            Image(
                                painter = painterResource(id = R.drawable.harvard), // Coloca la imagen adecuada
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentScale = ContentScale.Crop // Ajusta la escala si es necesario
                            )

                            // Texto de bienvenida centrado
                            Text(
                                text = "¡HOLA ESTUDIANTE!",
                                color = Color.White, // Ajusta el color según sea necesario
                                modifier = Modifier
                                    .align(Alignment.Center).padding(top = 60.dp), // Centra el texto en el Box
//        style = MaterialTheme.typography.h4 // Ajusta el tamaño y estilo
                            )
                        }
                    }
                    item {
                        SectionCard(
                            title = "PRACTICAR",
                            imageResource = R.drawable.practice_image,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    item {
                        SectionCard(
                            title = "PENDIENTES",
                            imageResource = R.drawable.pending_image,
                            modifier = Modifier.padding(16.dp).clickable { val intent = Intent(context, StudentsPending::class.java)
                                    context.startActivityWithAnimation(intent)}
                        )
                    }
                    item {
                        SectionCard(
                            title = "CALIFICACIONES",
                            imageResource = R.drawable.grades_image,
                            modifier = Modifier.padding(16.dp).clickable { val intent = Intent(context, StudentsScores::class.java)
                                context.startActivityWithAnimation(intent)}
                        )
                    }
                }
            }

            // Dialogo de salir
//            if (ExitDialog) {
//                AlertDialog(
//                    onDismissRequest = { ExitDialog = false },
//                    title = { Text(text = "¿Deseas salir de la aplicación?") },
//                    confirmButton = {
//                        Button(onClick = { ExitDialog = false }) {
//                            Text("Sí")
//                        }
//                    },
//                    dismissButton = {
//                        OutlinedButton(onClick = { ExitDialog = false }) {
//                            Text("No")
//                        }
//                    }
//                )
//            }
        }
    }
}

@Composable
fun SectionCard(title: String, imageResource: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp) // Ajusta la altura de las tarjetas
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White) // Coloca un color de fondo por defecto
            .clickable { /* Acción cuando se clickea la tarjeta */ }
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 20.dp, bottom = 6.dp),
//            style = MaterialTheme.typography.h5
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDashboard() {
    EnglishCoreAppKTheme {
        ShowStudentView()
    }
}


