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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Teachers.BottomNavigationBar
import com.example.englishcoreappk.Teachers.NavigationHost
import com.example.englishcoreappk.Teachers.ShowView
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class StudentsScores : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowStudentScores()
            }
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowStudentScores() {
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
                    Text(fontWeight = FontWeight.ExtraBold, fontSize = 25.sp, text = "CALIFICACIONES", modifier = Modifier.align(Alignment.CenterHorizontally).padding(top=16.dp))
                    Column (modifier = Modifier.fillMaxSize().background(Color.White)){
                        LazyColumn(modifier = Modifier.height(550.dp).background(Color.DarkGray).fillMaxWidth()) {
                            item {
                                ScoreCards(title = "Periodo Ago-Sep      97",
                                        modifier = Modifier.padding(top= 5.dp)
                                )

                            }
                            item {
                                ScoreCards(title = "Periodo Sep-Oct      99",
                                    modifier = Modifier.padding(top = 5.dp))
                            }
                        }
                        Row (modifier = Modifier.height(130.dp).padding(bottom = 10.dp).background(Color(0xff2e4053)).width(400.dp).clip(
                            RoundedCornerShape(bottomEnd = 40.dp, topEnd = 40.dp)
                        )){
                            Text(text= "Tu promedio final es de", color = Color.White, fontSize = 25.sp,modifier=Modifier.align(Alignment.CenterVertically).padding(start = 10.dp))
                            Text(text = "9.8", fontSize = 30.sp, color = Color.Yellow, modifier=Modifier.align(Alignment.CenterVertically).padding(start = 20.dp))
                        }
                        Text(text = "Total de faltas: 8", fontSize = 15.sp)
                    }
                }
            }


        }
    }

}

@Composable
fun ScoreCards(title: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp) // Ajusta la altura de las tarjetas
            .background(Color.LightGray) // Coloca un color de fondo por defecto
            .clickable { /* Acción cuando se clickea la tarjeta */ }
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp, bottom = 6.dp).fillMaxSize(),
//            style = MaterialTheme.typography.h5
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentScores() {
    EnglishCoreAppKTheme {
        ShowStudentScores()
    }
}


