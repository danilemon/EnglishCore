package com.example.englishcoreappk

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme
import com.example.englishcoreappk.Retrofit.LoginRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                Welcome()
            }
        }
    }
}

@Composable
fun Welcome() {
    var expanded by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var Register by remember  { mutableStateOf(true) }

    var Street by remember { mutableStateOf("") }
    var Date by remember{ mutableStateOf("") }
    var Phone by remember{ mutableStateOf("") }



    // Retrofit client instance
    val loginRepository = remember { LoginRepository() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.libertystatue),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Imagen del logo en la parte superior
        Image(
            painter = painterResource(id = R.drawable.englishcorelogo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(20.dp)
                .size(100.dp)
                .clip(CircleShape)
        )

        // Mostrar la tarjeta en el centro si el botón fue presionado
        if (expanded) {
            Register=false
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, colorResource(id = R.color.bluemarine)),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Campo de texto para el usuario
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Usuario") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Campo de texto para la contraseña
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Contraseña") },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Mostrar mensaje de error si existe
                        if (errorMessage.isNotEmpty()) {
                            Text(text = errorMessage, color = Color.Red)
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Botón de iniciar sesión
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                loginRepository.login(username, password) { success, error ->
                                    if (success) {
                                        errorMessage = ""  // Limpia el mensaje de error si el login es exitoso
                                    } else {
                                        errorMessage = error ?: "Error desconocido"
                                    }
                                }
                            }
                        ) {
                            Text("Iniciar sesión", color = colorResource(id = R.color.white))
                        }
                    }
                }
            }
        }

        if (Register) {
            expanded=false
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, colorResource(id = R.color.bluemarine)),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Registra tus datos",
                            color = Color.Gray,
                            fontSize = 25.sp,
                            modifier = Modifier
                                .background(Color.Transparent)  // Cambia el color de fondo
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(
                            value = username,
                            onValueChange = {username=it},
                            label={Text("Ingresa tu usuario")},
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(
                            value = password,
                            onValueChange = {password=it},
                            label={Text("Ingresa tu contrasenia")},
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(
                            value = Street,
                            onValueChange = {Street=it},
                            label={Text("Ingresa tu Direcion")},
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        var isFocused by remember { mutableStateOf(false) }
                        TextField(
                            value = Date,
                            onValueChange = {Date=it},
                                label = {
                                    // Cambiar el hint dependiendo del foco
                                    if (isFocused) {
                                        Text("DD/MM/AAAA")
                                    } else {
                                        Text("Ingresa tu fecha de cumpleanios")
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { focusState ->
                                        // Cambiar el estado de foco cuando el TextField recibe o pierde el foco
                                        isFocused = focusState.isFocused
                                    }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextField(
                            value = Phone,
                            onValueChange = {Phone=it},
                            label={Text("Ingresa tu Telfono o el de tu tutor")},
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        if (errorMessage.isNotEmpty()) {
                            ShowErrorMessage(errorMessage)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(onClick = {

                                loginRepository.Register(username, password,Date,Street,Phone) { success, error ->
                                    if (success) {
                                        errorMessage = ""  // Limpia el mensaje de error si el login es exitoso
                                    } else {
                                        errorMessage = error ?: "Error desconocido"
                                    }
                                }

                        }) { Text("Continuar") }





                    }

                }

            }
        }
        // Botón en la parte inferior
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                shape = RoundedCornerShape(16.dp),
                onClick = { Register = !Register },
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Text(if (Register) "Volver" else "Resgistrate", color = colorResource(id = R.color.bluemarine))
            }

            OutlinedButton(
                shape = RoundedCornerShape(16.dp),
                onClick = { expanded = !expanded },
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
            ) {
                Text(if (expanded) "Volver" else "Iniciar sesión", color = colorResource(id = R.color.bluemarine))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    EnglishCoreAppKTheme {
        Welcome()
    }
}

@Composable
fun ShowErrorMessage(errorMessage: String) {
        val context = LocalContext.current
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show() 
}
