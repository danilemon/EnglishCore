package com.example.englishcoreappk.Teachers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Scaffold (
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ){
            NavigationHost(navController)
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
                GroupItem(group, index % 4) // Asegúrate de que GroupItem tenga los parámetros correctos
            }
        }
    }
    }


}

@Composable
fun ActivitiesScreen() {
    Text(text = "Pantalla de perfil", modifier = Modifier.fillMaxSize())
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