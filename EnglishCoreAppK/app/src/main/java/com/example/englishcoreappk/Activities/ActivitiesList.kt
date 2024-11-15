package com.example.englishcoreappk.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Students.StudentsDashboard
import com.example.englishcoreappk.Students.StudentsProfile
import com.example.englishcoreappk.Students.startActivityWithAnimation
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class ActivitiesList: ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowSections()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowActivities() {
    val selectedTab = remember { mutableStateOf("Exercises") } // Estado para el tab seleccionado

    val context = LocalContext.current

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
                            .clickable { val intent = Intent(context, StudentsDashboard::class.java)
                                context.startActivityWithAnimation(intent)}
                    )
                    Image(
                        painter = painterResource(id = R.drawable.return_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopStart)
                            .clickable { val intent = Intent(context, SectionList::class.java)
                                context.startActivityWithAnimation(intent)}
                    )
                }
            },

            bottomBar = {
                BottomNavigationBar(
                    selectedTab = selectedTab.value,
                    onTabSelected = { selectedTab.value = it }
                )
            },
            


            ) {contentPadding ->
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
                    when (selectedTab.value) {
                        "Exercises" -> ExercisesContent()
                        "Exams" -> ExamsContent()
                    }
                }
            }


        }
    }

}

@Composable
fun ExamsContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "Dashboard Section",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


@Composable
fun ExercisesContent() {
    Box(modifier = Modifier.fillMaxSize()) {
    Text(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        text = "BASICS-Grammar",
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 16.dp)
    )
    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White)) {
        item { ExercisesCards(title = "1. BASICS", LevlColor = Color(0xffaed6f1)) }
        item { ExercisesCards(title = "2. INTERMEDIATE", LevlColor = Color(0xff5dade2)) }
        item { ExercisesCards(title = "3. ADVANCED", LevlColor = Color(0xff2e86c1)) }
        item { ExercisesCards(title = "4. EXPERT", LevlColor = Color(0xff21618c)) }
        item { ExercisesCards(title = "5. MASTER", LevlColor = Color(0xff34495e)) }
    }
}
}


@Composable
fun ExercisesCards(title: String, modifier: Modifier = Modifier, LevlColor: Color) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp) // Ajusta la altura de las tarjetas
            .padding(5.dp)
            .background(LevlColor) // Coloca un color de fondo por defecto
            .clickable { /* Acción cuando se clickea la tarjeta */ }
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp, bottom = 6.dp),
        )
    }
}

@Composable
fun BottomNavigationBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    NavigationBar(
        modifier = Modifier
            .height(100.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
        containerColor = Color(0xffd6eaf8)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.exercises_icon),
                    contentDescription = "Exercises",
                    modifier = Modifier.size(50.dp)
                )
            },
            selected = selectedTab == "Exercises",
            onClick = { onTabSelected("Exercises") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.test_icon),
                    contentDescription = "Exams",
                    modifier = Modifier.size(50.dp)
                )
            },
            selected = selectedTab == "Exams",
            onClick = { onTabSelected("Exams") }
        )

    }
}
@Preview(showBackground = true)
@Composable
fun PreviewExercises() {
    EnglishCoreAppKTheme {
        ShowActivities()
    }
}