package com.example.englishcoreappk.Teachers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.englishcoreappk.Retrofit.Groups
import com.example.englishcoreappk.Retrofit.TeacherRepository
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme


@Composable
fun GroupsScreen(navController: NavController) {

    val groupsState = remember { mutableStateOf<List<Groups>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) } // Estado para cargar
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Llama a GetGroupsRequest cuando se compone el Composable
        LaunchedEffect(Unit) {
            TeacherRepository.GetGroupsRequest { groups ->
                groupsState.value = groups // Actualiza el estado con los grupos obtenidos
                isLoading.value = false // Cambia el estado de carga
            }
        }

        // Muestra un indicador de carga mientras se obtienen los datos
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center) )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                itemsIndexed(groupsState.value) { index, group ->
                    GroupItem(group, (index+1) % 4){
                        navController.navigate("Grupos/${group.ID}")
                    } // Asegúrate de que GroupItem tenga los parámetros correctos
                }
            }
        }
    }
}

@Composable
fun GroupItem(Group: Groups,BGcolor:Int,GroupMenu:()->Unit){
    val ColorMap= mapOf(
        0 to Color(0xFFDB162F),
        1 to Color(0xFF2F2A50),
        2 to Color(0xFFC4C6E7),
        3 to Color(0XFF3A6EA5)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {GroupMenu()}
        ,
        colors = CardDefaults.cardColors(
            containerColor = ColorMap[BGcolor] ?: Color.White  // Cambia el color de fondo aquí
        ),



        ) {
        Column(
            modifier = Modifier.padding(16.dp)

        ) {
            Text(text = "Grupo:\n nivel ${Group.Level.toString()} / ${Group.Days} / ${Group.Hours}")
        }
    }


}



@Composable
fun GroupMenu(GroupID: Int){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

            Text(text = "Nivel - 1",
                style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "L-J-V / 3:00 - 5:00"
            )

        Column(
            modifier = Modifier.fillMaxSize(), // Ocupa todo el tamaño disponible
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Top // Comienza desde la parte superior
        ) {
            // Primer Box
            Box(
                modifier = Modifier
                    .weight(1f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xffDB162F)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Lista de alumnos", color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }

            // Segundo Box
            Box(
                modifier = Modifier
                    .weight(1f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xffC4C6E7)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Tomar Asistencia",color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }

            // Tercer Box
            Box(
                modifier = Modifier
                    .weight(0.5f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xff3A6EA5)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Tareas", color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }

            Box(
                modifier = Modifier
                    .weight(0.5f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xff3A6EA5)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Examenes", color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupPreview() {
    EnglishCoreAppKTheme {
        GroupMenu(1)
    }
}