package com.example.englishcoreappk.Students

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Students.ui.theme.EnglishCoreAppKTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.englishcoreappk.Activities.StudentAsignedActivities
import com.example.englishcoreappk.Retrofit.UserData

class StudentAsignedViews : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AsignedView()
        }
    }
}

@Composable
fun AsignedView(){
    val context = LocalContext.current

    var Loading by remember{ mutableStateOf(true)}
    var Studnet by remember { mutableStateOf<StudentData?>(null) }
    LaunchedEffect(Unit) {
        StudentRepository.GetStudentDataRequest(studentID = UserData.User) { studentData ->
            Studnet = studentData
            Loading = false
        }
    }
    var navController = rememberNavController()
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
                            .clickable {
                                val intent = Intent(context, StudentsDashboard::class.java)
                                context.startActivity(intent)
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
                                context.startActivity(intent)
                            }
                    )
                }
            },


            ) { contentPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .background(Color(0xffD9D9D9))
            ) {
                if(Loading){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }else
                {
                    GroupsNavigationHost(navController,Studnet!!.GroupID)
                }
            }
        }
        }
}


@Composable
fun GroupsNavigationHost(navController: NavHostController,GroupID: String){
    NavHost(navController,startDestination="Menu"){
        composable("Menu") {
            SelectLevel(navController)
        }
        composable("Activities"){
            StudentAsignedActivities(GroupID)
        }
    }
}

@Composable
fun SelectLevel(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffD9D9D9))
    ){
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 40.dp,
                        topEnd = 40.dp
                    )
                )
                .fillMaxSize()
                .background(Color.White)
                .padding(vertical = 25.dp),
            verticalArrangement = Arrangement.SpaceEvenly, // Distribute space evenly, including padding
            horizontalAlignment = Alignment.CenterHorizontally // Align content in the center horizontally
        ) {
            // Box 1
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Equal space for all boxes
                    .background(Color(0xFFDB162F))
                    .clickable{
                        navController.navigate("Activities")
                    }
            ) {
                Text(
                    text = "Examenes",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center) // Center the text in the box
                )
            }
            Spacer(modifier = Modifier.height(25.dp)) // Space between boxes
            // Box 2
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFC4C6E7))
                    .clickable{
                        navController.navigate("Activities")
                    }
            ) {
                Text(
                    text = "Actividades",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(25.dp)) // Space between boxes
            // Box 3
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF3A6EA5))
                    .clickable{
                        navController.navigate("Activities")
                    }
            ) {
                Text(
                    text = "Practicas",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EnglishCoreAppKTheme {
        //SelectLevel()
    }
}