package com.example.englishcoreappk.Students

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.englishcoreappk.R
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


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ShowProfileView() {
        var ExitDialog by remember { mutableStateOf(true) }

        BackHandler(enabled = ExitDialog) {
            ExitDialog = true
        }

        Box(modifier = Modifier.fillMaxSize().background(Color(0xffD9D9D9))) {
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

                    }
                },


                ) {
                Column(modifier = Modifier.fillMaxWidth().height(500.dp).clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                ) {
                    // Imagen de fondo principal
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp).padding(top= 100.dp) // Ajusta la altura de la imagen
                    ) {


                    }

                    // Tarjetas de Practicar, Pendientes y Calificaciones

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


    @Preview(showBackground = true)
    @Composable
    fun PreviewProfile() {
        EnglishCoreAppKTheme {
            ShowProfileView()
        }
    }


