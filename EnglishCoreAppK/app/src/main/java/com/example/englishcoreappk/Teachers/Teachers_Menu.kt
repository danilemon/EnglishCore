package com.example.englishcoreappk.Teachers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.englishcoreappk.Retrofit.Groups
import com.example.englishcoreappk.Retrofit.TeacherRepository
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.R
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme


data class BottomNavItem(
    val title:String,
    val Icon:ImageVector,
    val Route:String
)

class Teachers_Menu : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowView()
        }
    }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowView(){
    val navController = rememberNavController()
    var ExitDIialog by remember { mutableStateOf(false) }

    BackHandler(enabled = ExitDIialog) {
        ExitDIialog=true
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold (

            bottomBar = {
                BottomNavigationBar(navController)
            },
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)// altura de la barra superior
                        .background(Color(0xffD9D9D9))
                        .padding(top = 5.dp)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.englishcorelogo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp) // Tamaño de la imagen
                            .clip(CircleShape) // Aplica la forma circular
                            .align(Alignment.TopCenter)  // Alinea la imagen en el centro del Box
                           // Ajusta la posición vertical de la imagen para estar entre el top y el Box blanco

                    )
                    Image(
                        painter = painterResource(id=R.drawable.baseline_exit_to_app_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp) // Tamaño de la imagen
                            .clip(CircleShape)
                            .align(Alignment.TopStart)
                            .clickable { ExitDIialog = !ExitDIialog }
                    )

                    // Puedes agregar aquí contenido adicional si lo necesitas
                  Box(modifier = Modifier
                      .fillMaxWidth()
                      .height(20.dp)
                      .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                      .background(Color.White)
                      .align(Alignment.BottomCenter)


                  )
                }}
        ){paddingValues ->
            // Box que ajusta su tamaño según el espacio disponible entre la topBar y la bottomBar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Usa paddingValues proporcionado por el Scaffold
            ) {
                NavigationHost(navController)

                if (ExitDIialog) {
                    AlertDialog(
                        onDismissRequest = { ExitDIialog = false },
                        title = { Text(text = "Deseas salir de la aplicación") },
                        confirmButton = {
                            Button(onClick = { ExitDIialog = false }) {
                                Text("Sí")
                            }
                        },
                        dismissButton = {
                            OutlinedButton(onClick = { ExitDIialog = false }) {
                                Text("No")
                            }
                        }
                    )
                }
            }
        }
    }

}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items= listOf(
        BottomNavItem(
            "Grupos",
            Icons.Default.Menu,
            "Grupos"
        ),
        BottomNavItem(
            "Actividades",
            Icons.Default.Edit,
            "Actividades",
        ),
        BottomNavItem(
            "Practicas",
            Icons.Default.AccountBox,
            "Practicas"
        )
    )

    val currentRoute = currentRoute(navController)

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.Route,
                onClick = {
                    if (currentRoute != item.Route) {
                        navController.navigate(item.Route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                label = { Text(text = item.title) },
                icon = { Icon(imageVector = item.Icon, contentDescription = item.title) }
            )
        }
    }

}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(navController, startDestination = "Grupos") {
        composable("Grupos") {
            GroupsScreen()
        }
        composable("Actividades") {
            ActivitiesScreen()
        }
        composable("Practicas") {
            PracticeScreen()
        }
    }
}
@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun GroupsScreen() {

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
                GroupItem(group, (index+1) % 4) // Asegúrate de que GroupItem tenga los parámetros correctos
            }
        }
    }
    }
}

@Composable
fun ActivitiesScreen() {
    Box(
        modifier=Modifier
            .fillMaxSize()
    ){
        Column(

            modifier=Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(modifier = Modifier
                .weight(1f)

                .padding(5.dp),){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id=R.drawable.activities_img),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Transparent, Color(0xFFDB162F).copy(alpha = 0.2f)), // Degradado de transparente a rojo oscuro
                                start = Offset(0f, 0f), // Comienzo del degradado
                                end = Offset(0f, Float.POSITIVE_INFINITY) // Final del degradado, de arriba a abajo
                            )
                        )
                ){
                Text(text = "Actividades",
                    color=Color.White,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .align(Alignment.Center) // Alineación del texto en la esquina inferior izquierda
                        .padding(8.dp) // Agrega padding alrededor del texto
                )}
            }

            Box(modifier = Modifier
                .weight(1f)

                .padding(5.dp),){
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id=R.drawable.exam_img),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Transparent, Color(0xFF3A6EA5).copy(alpha = 0.4f)), // Degradado de transparente a rojo oscuro
                                start = Offset(0f, 0f), // Comienzo del degradado
                                end = Offset(0f, Float.POSITIVE_INFINITY) // Final del degradado, de arriba a abajo
                            )
                        )
                ){
                Text(text = "Actividades",
                    color=Color.White,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .align(Alignment.Center) // Alineación del texto en la esquina inferior izquierda
                        .padding(8.dp) // Agrega padding alrededor del texto
                )}
            }

            Box(modifier = Modifier
                .weight(1f)
                .padding(5.dp),){
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id=R.drawable.practica_img),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color.Transparent, Color(0xFF74007A).copy(alpha = 0.4f)), // Degradado de transparente a rojo oscuro
                                start = Offset(0f, 0f), // Comienzo del degradado
                                end = Offset(0f, Float.POSITIVE_INFINITY) // Final del degradado, de arriba a abajo
                            )
                        )
                ){
                    Text(text = "Practicas",
                        color=Color.White,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .align(Alignment.Center) // Alineación del texto en la esquina inferior izquierda
                            .padding(8.dp) // Agrega padding alrededor del texto
                    )}
            }
        }

    }
}

@Composable
fun PracticeScreen() {
    Text(text = "Pantalla de configuración", modifier = Modifier.fillMaxSize())
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    EnglishCoreAppKTheme {
        ShowView()
    }
}


@Composable
fun GroupItem(Group: Groups,BGcolor:Int){
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