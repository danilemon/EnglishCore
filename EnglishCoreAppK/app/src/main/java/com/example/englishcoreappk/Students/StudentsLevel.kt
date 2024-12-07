package com.example.englishcoreappk.Students

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.englishcoreappk.Activities.SectionList
import com.example.englishcoreappk.R
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class StudentsLevel: ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                ShowStudentLevel()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ShowStudentLevel() {
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
                                context.startActivity(intent)}
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profile_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopStart)
                            .clickable { val intent = Intent(context, StudentsProfile::class.java)
                                context.startActivity(intent)}
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
                    Text(fontWeight = FontWeight.ExtraBold, fontSize = 25.sp, text = "Selecciona un nivel", modifier = Modifier.align(
                        Alignment.CenterHorizontally).padding(top=16.dp))
                    LazyColumn (modifier = Modifier.fillMaxSize().background(Color.White)){
                        item {
                            LevelCards(title = "1. BASICS", LevlColor = Color(0xffaed6f1), onClick = {
                                val intent = Intent(context, StudentAsignedViews::class.java)
                                context.startActivity(intent)
                            })
                        }
                        item {
                            LevelCards(title = "2. INTERMEDIATE", LevlColor = Color(0xff5dade2), onClick = {
                                val intent = Intent(context, StudentAsignedViews::class.java)
                                context.startActivity(intent)
                            })

                        }
                        item {
                            LevelCards(title = "3. ADVANCED", LevlColor = Color(0xff2e86c1), onClick = {
                                val intent = Intent(context, StudentAsignedViews::class.java)
                                context.startActivity(intent)
                            })
                        }
                        item {
                            LevelCards(title = "4. EXPERT", LevlColor = Color(0xff21618c), onClick = {
                                val intent = Intent(context, StudentAsignedViews::class.java)
                                context.startActivity(intent)
                            })
                        }
                        item {
                            LevelCards(title = "5. MASTER", LevlColor = Color(0xff34495e), onClick = {
                                val intent = Intent(context, StudentAsignedViews::class.java)
                                context.startActivity(intent)
                            })
                        }



                    }
                }
            }


        }
    }

}

@Composable
fun LevelCards(title: String, modifier: Modifier = Modifier, LevlColor: Color, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp) // Ajusta la altura de las tarjetas
            .background(LevlColor) // Coloca un color de fondo por defecto
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 20.dp, bottom = 6.dp),
//            style = MaterialTheme.typography.h5
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStudentLevel() {
    EnglishCoreAppKTheme {
        ShowStudentLevel()
    }
}