package com.example.englishcoreappk.Activities

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
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Students.LevelCards
import com.example.englishcoreappk.Students.ShowStudentLevel
import com.example.englishcoreappk.Students.StudentsDashboard
import com.example.englishcoreappk.Students.StudentsLevel
import com.example.englishcoreappk.Students.StudentsProfile
import com.example.englishcoreappk.Students.StudentsScores
import com.example.englishcoreappk.Students.startActivityWithAnimation
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class SectionList: ComponentActivity() {
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
fun ShowSections() {
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
                            .clickable { val intent = Intent(context, StudentsLevel::class.java)
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
                    Text(fontWeight = FontWeight.ExtraBold, fontSize = 25.sp, text = "BASICS - Sections", modifier = Modifier.align(
                        Alignment.CenterHorizontally).padding(top=16.dp))
                    LazyColumn (modifier = Modifier.fillMaxSize().background(Color.White)){
                        item {
                            SectionCards(position = "1", title = "BASICS", onClick = {
                                //val intent = Intent(context, ActivitiesList::class.java)
                                //context.startActivity(intent)
                            })
                        }
                        item {
                            SectionCards(position = "2", title = "INTERMEDIATE", onClick = {
                                //val intent = Intent(context, ActivitiesList::class.java)
                                //context.startActivity(intent)
                            })

                        }
                        item {
                            SectionCards(position = "3",title = "ADVANCED", onClick = {
                                // intent = Intent(context, ActivitiesList::class.java)
                                //context.startActivity(intent)
                            })
                        }
                        item {
                            SectionCards(position = "4", title = "EXPERT", onClick = {
                                //val intent = Intent(context, ActivitiesList::class.java)
                                //context.startActivity(intent)
                            })
                        }
                        item {
                            SectionCards(position = "5", title = "MASTER", onClick = {
                                //val intent = Intent(context, ActivitiesList::class.java)
                                //context.startActivity(intent)
                            })
                        }



                    }
                }
            }


        }
    }

}

@Composable
fun SectionCards(title: String, modifier: Modifier = Modifier, position: String, onClick: ()-> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(60.dp) // Ajusta la altura de las tarjetas
            .background(Color(0xff21618c)) // Coloca un color de fondo por defecto
            .clickable { onClick() }
    ) {
        Row (modifier= Modifier.align(Alignment.CenterStart)){
            Text(
                text = position,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start= 10.dp)
            )
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.padlock_icon),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp)


        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSections() {
    EnglishCoreAppKTheme {
        ShowSections()
    }
}