package com.example.englishcoreappk.Students
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

import GetStudentTickets
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Retrofit.UserData
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme
import kotlinx.serialization.descriptors.PrimitiveKind

object SelectedImageManager {
    val selectedImageUri = mutableStateOf<String?>(null)
}

class StudentsTickets: ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowReceiptsView()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data?.toString()
            selectedImageUri?.let {
                // Guarda la URI en un estado compartido o pásala a tu Composable
                SelectedImageManager.selectedImageUri.value = it
            }
        }
    }
}

fun uploadImageToFirebase(uri: String, userDocId: String, onComplete: (String?) -> Unit) {
    // Genera un nombre único para el archivo
    val fileName = UUID.randomUUID().toString() + ".jpg"

    // Referencia a la ubicación en Firebase Storage
    val storageRef = FirebaseStorage.getInstance().reference.child("tickets_images/$userDocId/$fileName")

    // Subir la imagen
    storageRef.putFile(Uri.parse(uri))
        .addOnSuccessListener {
            // Obtener la URL de la imagen subida
            storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                // Retornar la URL de la imagen subida
                onComplete(downloadUri.toString())
            }
        }
        .addOnFailureListener { exception ->
            // Manejar el error
            onComplete(null)
        }
}

fun saveTicketToFirestore(userDocId: String, imageUrl: String, description: String) {
    val db = FirebaseFirestore.getInstance()

    // Obtener el ID del ticket (puedes usar un UUID o un ID autogenerado por Firestore)
    val ticketId = UUID.randomUUID().toString()

    // Crear el documento con la URL de la imagen, descripción y fecha
    val ticketData = hashMapOf(
        "Description" to description,
        "ImageURL" to imageUrl,
        "Date" to System.currentTimeMillis() // Usamos la fecha actual como timestamp
    )

    // Guardar en la subcolección "tickets" del usuario
    db.collection("users") // La colección de usuarios
        .document(userDocId) // Documento del usuario
        .collection("tickets") // Subcolección "tickets"
        .document(ticketId) // ID del ticket
        .set(ticketData)
        .addOnSuccessListener {
            // El ticket se guardó correctamente
            println("Ticket guardado en Firestore")
        }
        .addOnFailureListener { e ->
            // Manejar el error
            println("Error al guardar el ticket: ${e.message}")
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowReceiptsView(){
    val context = LocalContext.current
    val userrrr = UserData.User
    var isLoadingProfile = remember { mutableStateOf(false) } // Estado para cargar
    var ticketsList by remember { mutableStateOf<List<GetStudentTickets>?>(emptyList()) }
    var ticketDesc by remember { mutableStateOf(TextFieldValue("")) }
    var enlargedImageUrl by remember { mutableStateOf<String?>(null) }
    val selectedImageUri = SelectedImageManager.selectedImageUri.value

    if (enlargedImageUrl != null) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = { enlargedImageUrl = null }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)) // Fondo semitransparente
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = rememberImagePainter(data = enlargedImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .padding(16.dp)
                )
            }
        }
    }

    // Cargar datos del estudiante y recordatorios




    Box(modifier = Modifier.fillMaxSize().background(Color(0xffD9D9D9))) {


        LaunchedEffect(Unit) {
            StudentRepository.GetStudentTickets(studentDocID = userrrr) { tickets ->
                println("Reminders recibidos del servidor: $tickets")

                if (tickets == null || tickets.isEmpty()) {
                    println("No se encontraron recordatorios o los datos son nulos.")
                    ticketsList = emptyList()
                } else {
                    // Filtrar y limpiar datos nulos
                    ticketsList = tickets.mapNotNull { ticket ->
                        if (ticket.Description != null && ticket.ImageURL != null && ticket.Date != null) {
                            ticket
                        } else {
                            println("Recordatorio inválido encontrado: $ticket")
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

                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_PICK).apply { type = "image/*" }
                            (context as? Activity)?.startActivityForResult(intent, 1001)
                        },
                        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                        text = { Text(text = "Upload a ticket") },
                    )
                }



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
                            text = "Tickets",
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                        )
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .background(Color(0xff34495e))
                                .fillMaxSize()
                        ) {
                            item{
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Mostrar y eliminar la imagen seleccionada
                                    SelectedImageManager.selectedImageUri.value?.let { uri ->
                                        Image(
                                            painter = rememberImagePainter(data = uri),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(200.dp)
                                                .padding(16.dp)
                                        )
                                    Row(modifier = Modifier.padding(10.dp)) {
                                        OutlinedTextField(value= ticketDesc,
                                                onValueChange = { ticketDesc = it },
                                            label = { Text("Description") })
                                    }
                                    Row(modifier = Modifier.padding(10.dp)) {
                                        Button(
                                            onClick = {   if (selectedImageUri != null) {
                                                isLoadingProfile.value = true
                                                // Subir la imagen a Firebase Storage
                                                uploadImageToFirebase(selectedImageUri, userrrr) { imageUrl ->
                                                    isLoadingProfile.value = false
                                                    if (imageUrl != null) {
                                                        // Si la imagen se sube correctamente, guardamos el ticket en Firestore
                                                        saveTicketToFirestore(userrrr, imageUrl, ticketDesc.text)
                                                        Toast.makeText(context,"The ticket has been uploaded successfully", Toast.LENGTH_SHORT)
                                                    } else {
                                                        // Manejo de error si no se pudo subir la imagen
                                                        println("Error al subir la imagen.")
                                                    }
                                                }
                                            }
                                                      },
                                            modifier = Modifier.padding(end = 8.dp)
                                        ) {
                                            Text("Upload ticket")
                                        }
                                        Button(
                                        onClick = { SelectedImageManager.selectedImageUri.value = null },
                                        modifier = Modifier.padding(start = 8.dp).background(Color.Transparent)
                                    ) {
                                        Text("Cancel upload", color = Color.White)
                                    }

                                         }

                                    } ?: run {
                                        Text("Select a photo for uploading a ticket", Modifier.padding(16.dp), color = Color.White)
                                    }
                                }
                        }
                            if (ticketsList.isNullOrEmpty()) {

                                item {
                                    Text(
                                        text = "You haven't uploaded a ticket yet.",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .align(Alignment.CenterHorizontally)
                                    )
                                }
                            } else {
                                ticketsList?.forEach { ticket ->
                                    item {
                                        StudentTickets(
                                            title = ticket.Description,
                                            date = ticket.Date,
                                            imageUrl = ticket.ImageURL, // Aquí pasamos la URL de la imagen
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth()
                                                .clickable { enlargedImageUrl = ticket.ImageURL } // Detecta el tap

                                        )
                                    }
                                }

                            }

                        }
                    }
                }

            }
        }
    }

}


@Composable
fun StudentTickets(title: String, date: String, imageUrl: String, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp) // Ajusta la altura de las tarjetas
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp) // Añadir padding para que no quede pegado a los bordes

    ) {
        // Imagen
        Image(
            painter = rememberImagePainter(data = imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp) // Tamaño de la imagen
                .clip(RoundedCornerShape(10.dp)) // Forma de la imagen
        )

        // Espaciado entre la imagen y el texto
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically) // Alinea el texto verticalmente con la imagen
        ) {
            // Título del ticket
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                maxLines = 2 // Limitar a una línea
            )

            // Fecha del ticket
            Text(
                text = date,
                color = Color.Gray,
                maxLines = 1 // Limitar a una línea
            )
        }
    }

}




@Preview(showBackground = true)
@Composable
fun PreviewTickets() {
    EnglishCoreAppKTheme {
        ShowReceiptsView()
    }
}

