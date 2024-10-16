package com.example.englishcoreappk.Teachers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.englishcoreappk.Welcome
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

    var SelectedMenu by remember { mutableStateOf(0) }
    NavigationBar {
        items.forEachIndexed{index,item->
            NavigationBarItem(
                selected = SelectedMenu==index,
                onClick = {
                    SelectedMenu=index
                    navController.navigate(item.Route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                label = {Text(text=item.title)},
                icon = {
                    Icon(imageVector = item.Icon, contentDescription = item.title)
                }
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
    Text(text = "Pantalla de inicio", modifier = Modifier.fillMaxSize())
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