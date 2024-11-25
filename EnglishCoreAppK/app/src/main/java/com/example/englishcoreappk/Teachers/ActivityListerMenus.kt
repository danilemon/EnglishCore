package com.example.englishcoreappk.Teachers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun assignActivity(Dismiss:()->Unit){
    Box(modifier = Modifier.wrapContentHeight()
        .width(400.dp).clip(RoundedCornerShape(16.dp)
            ).background(Color.White)){
        Column(Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFDB162F)), // Fondo de la Box
                contentAlignment = Alignment.Center // Alineación central del contenido
            ) {
                Text(
                    text = "Asignar Tarea",
                    color = Color.White, // Color del texto
                    fontSize = 16.sp, // Tamaño del texto
                    textAlign = TextAlign.Center // Alineación del texto dentro del espacio
                )
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Distribuye con espacio entre grupos
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primer grupo: "Tema" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Tema",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = listOf("Opción 1", "Opción 2"), Index = 0, Saved = "Opción 1")
                }

                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el primer grupo y el segundo

                // Segundo grupo: "Tarea" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Tarea",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = listOf("Opción A", "Opción B"), Index = 0, Saved = "Opción A")
                }
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Grupo",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                )
                Spiner(Options = listOf("Grupo 1", "Grupo 2"), Index = 0, Saved = "Grupo 1")
            }

            Button(modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
                .height(50.dp), onClick = {Dismiss()},colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDB162F)),) {
                Text(
                    text = "Guardar",
                    color = Color.White // Color del texto
                )
            }
        }
    }
}

@Composable
fun assignExam(Dismiss:()->Unit){
    Box(modifier = Modifier.wrapContentHeight()
        .width(400.dp).clip(RoundedCornerShape(16.dp)).background(Color.White)){
        Column(Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF3A6EA5)), // Fondo de la Box
                contentAlignment = Alignment.Center // Alineación central del contenido
            ) {
                Text(
                    text = "Asignar Examen",
                    color = Color.White, // Color del texto
                    fontSize = 16.sp, // Tamaño del texto
                    textAlign = TextAlign.Center // Alineación del texto dentro del espacio
                )
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Distribuye con espacio entre grupos
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primer grupo: "Tema" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Tema",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 16.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = listOf("Opción 1", "Opción 2"), Index = 0, Saved = "Opción 1")
                }

                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el primer grupo y el segundo

                // Segundo grupo: "Tarea" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Tarea",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 16.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = listOf("Opción A", "Opción B"), Index = 0, Saved = "Opción A")
                }
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Grupo",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                )
                Spiner(Options = listOf("Grupo 1", "Grupo 2"), Index = 0, Saved = "Grupo 1")
            }
            var Min by remember{mutableStateOf(60)}
            var Tries by remember{mutableStateOf(1)}
            Row(modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                TextField(modifier = Modifier
                    .height(80.dp).weight(1f).padding(10.dp),value=Min.toString(),onValueChange={i->Min=i.toInt()},label={Text("Minutos")}, singleLine = true)
                TextField(modifier = Modifier
                    .height(80.dp).weight(1f).padding(10.dp),value=Tries.toString(),onValueChange={i->Tries=i.toInt()},label={Text("Intentos")}, singleLine = true)
            }

            Button(modifier = Modifier.fillMaxWidth(0.5f)
                .padding(20.dp)
                .height(50.dp).align(Alignment.CenterHorizontally)
                , onClick = {},colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A6EA5)),) {
                Text(
                    text = "Fecha",
                    color = Color.White // Color del texto
                )
            }
            Button(modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
                .height(50.dp), onClick = {Dismiss()},colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A6EA5)),) {
                Text(
                    text = "Guardar",
                    color = Color.White // Color del texto
                )
            }
        }
    }
}

@Composable
fun assignPractice(Dismiss:()->Unit){
    Box(modifier = Modifier.wrapContentHeight()
        .width(400.dp).clip(RoundedCornerShape(16.dp)).background(Color.White)){
        Column(Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF74007A)), // Fondo de la Box
                contentAlignment = Alignment.Center // Alineación central del contenido
            ) {
                Text(
                    text = "Asignar Practica",
                    color = Color.White, // Color del texto
                    fontSize = 16.sp, // Tamaño del texto
                    textAlign = TextAlign.Center // Alineación del texto dentro del espacio
                )
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Distribuye con espacio entre grupos
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primer grupo: "Tema" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Alumno",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = listOf("Opción 1", "Opción 2"), Index = 0, Saved = "Opción 1")
                }

                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el primer grupo y el segundo

                // Segundo grupo: "Tarea" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Practica",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = listOf("Opción A", "Opción B"), Index = 0, Saved = "Opción A")
                }
            }
            Spacer(Modifier.height(15.dp))

            Button(modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
                .height(50.dp), onClick = {Dismiss()},colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF74007A)),) {
                Text(
                    text = "Guardar",
                    color = Color.White // Color del texto
                )
            }
        }
    }
}



@Composable
fun Spiner(Options:List<String>,Index:Int,Saved:String) {

    var expanded by remember { mutableStateOf(false) }
    var Selected by remember { mutableStateOf("---") }


    Box(
        modifier = Modifier
            .width(75.dp)
            .border(1.dp, Color.Gray,) // Borde similar a un Spinner
            .clickable {
                expanded = !expanded
            } // Asegura que el tamaño del menú se ajuste al contenido
    ) {
        // Componente que actúa como el botón desplegable
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = Selected,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f) // Alinea el texto a la izquierda
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = Color.Gray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentWidth()
        ) {
            Options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        Selected = option
                        expanded = false
                    }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewAct(){
    assignExam(){

    }
}