package com.example.englishcoreappk.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.Helpers.SpinerItem
import com.example.englishcoreappk.Helpers.Spiner
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Retrofit.ActivityPreview
import com.example.englishcoreappk.Retrofit.ActivityRepository
import com.example.englishcoreappk.Retrofit.AsignedView
import com.example.englishcoreappk.Retrofit.UnitViews
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
                ShowActivities()
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
                            .clickable {
                                val intent = Intent(context, StudentsDashboard::class.java)
                                context.startActivityWithAnimation(intent)
                            }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.return_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .align(Alignment.TopStart)
                            .clickable {
                                val intent = Intent(context, SectionList::class.java)
                                context.startActivityWithAnimation(intent)
                            }
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .background(Color(0xffD9D9D9))
            )
            {
//                Column(
//                    modifier = Modifier.clip(
//                        RoundedCornerShape(
//                            topStart = 40.dp,
//                            topEnd = 40.dp
//                        )
//                    ).fillMaxSize().background(Color.White)
//                )
//                {
//                    when (selectedTab.value) {
//                        "Exercises" -> ExercisesContent()
//                        "Exams" -> ExamsContent()
//                    }
//                }
            }


        }
    }

}

//@Composable
//fun ExamsContent() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        Column {
//            Text(
//                text = "BASICS-Grammar",
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
//            )
//            Text(
//                text = "Exams",
//                fontWeight = FontWeight.Bold,
//                fontSize = 15.sp,
//                modifier = Modifier.padding(top = 6.dp).align(Alignment.CenterHorizontally)
//            )
//            LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).padding(top = 20.dp)) {
//                item { ExamsCards(title = "Exam 1" ) }
//                item { ExamsCards(title = "Exam 2") }
//                item { ExamsCards(title = "Exam 3") }
//                item { ExamsCards(title = "Exam 4") }
//                item { ExamsCards(title = "Exam 5") }
//            }
//        }
//
//    }
//}
//
//
//@Composable
//fun ExercisesContent() {
//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
//        Column{
//            Text(
//                fontWeight = FontWeight.Bold,
//                fontSize = 20.sp,
//                text = "BASICS-Grammar",
//                modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
//            )
//            Text(
//                fontWeight = FontWeight.Bold,
//                fontSize = 15.sp,
//                text = "Exercises",
//                modifier = Modifier.padding(top = 6.dp).align(Alignment.CenterHorizontally)
//            )
//            LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).padding(top = 20.dp)) {
//                item { ExercisesCards(title = "Exercise 1" ) }
//                item { ExercisesCards(title = "Exercise 2") }
//                item { ExercisesCards(title = "Exercise 3") }
//                item { ExercisesCards(title = "Exercise 4") }
//                item { ExercisesCards(title = "Exercise 5") }
//            }
//        }
//
//
//}
//}


@Composable
fun AsignedActsView(ID: String,Exam: Boolean,Act: Boolean,Next:(Act: String)-> Unit) {

    var Loading by remember { mutableStateOf(true) }
    var ActsList by remember { mutableStateOf<MutableList<AsignedView>>(mutableListOf()) }
    var UnitsList by remember { mutableStateOf<List<UnitViews>>(mutableListOf()) }

    var HeaderText by remember {mutableStateOf("")}
    Box(modifier = Modifier.fillMaxSize()) {
        LaunchedEffect(Unit) {
            if (Exam) {
                ActivityRepository.GetExamAsigned(ID) { It ->
                    HeaderText = "Examenes Asignados"
                    Loading = false
                    ActsList = It as MutableList<AsignedView>
                }
            } else if (Act) {
                ActivityRepository.GetActsAsigned(ID) { It ->
                    HeaderText = "Actividades Asignadas"
                    Loading = false
                    UnitsList = It
                    ActsList = UnitsList[0].Acts as MutableList<AsignedView>
                }
            } else {
                ActivityRepository.GetPracticesAsigned(ID) { It ->
                    HeaderText = "Practicas Asignadas"
                    Loading = false
                    ActsList = It as MutableList<AsignedView>
                }
            }
        }
        if (Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {
                    if (Act) {
                        var Items by remember {
                            mutableStateOf<MutableList<SpinerItem>>(
                                mutableListOf()
                            )
                        }
                        UnitsList.forEach { it ->
                            Items.add(SpinerItem(it.ID, it.Name))
                        }
                        Spiner(Items,BoxMod= Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 10.dp)){i->
                            ActsList=UnitsList[i].Acts as MutableList<AsignedView>
                        }
                        Text(
                            HeaderText,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp)
                                .offset(x = 10.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }else{
                        Text(
                            HeaderText,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Center)
                                .padding(horizontal = 20.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
                LazyColumn (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top){
                    itemsIndexed(ActsList){index,item ->
                        val context = LocalContext.current
                        if(Act){
                            ExercisesCards(item.Act.Name){
                                if(!item.HasAnswers){
                                            Toast.makeText(context,"La actividad no tiene respuestas",Toast.LENGTH_SHORT).show()
                                }else{
                                    Next(item.Act.ID)
                                }
                            }
                        }else if(Exam){
                            ExamsCards(item.Act.Name){
                                if(!item.HasAnswers){
                                    Toast.makeText(context,"La actividad no tiene respuestas",Toast.LENGTH_SHORT).show()
                                }else{
                                    Next(item.Act.ID)
                                }
                            }
                        }else{
                            ExercisesCards(item.Act.Name){
                                if(!item.HasAnswers){
                                    Toast.makeText(context,"La actividad no tiene respuestas",Toast.LENGTH_SHORT).show()
                                }else{
                                    Next(item.Act.ID)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExercisesCards(title: String, modifier: Modifier = Modifier,ShowAct:  ()->Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp) // Ajusta la altura de las tarjetas
            .padding(5.dp)
            .background(Color(0xff34495e)) // Coloca un color de fondo por defecto
            .clickable { ShowAct() }
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp),
        )
    }
}

@Composable
fun ExamsCards(title: String, modifier: Modifier = Modifier,ShowAct:()->Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp) // Ajusta la altura de las tarjetas
            .padding(5.dp)
            .background(Color(0xff2e86c1)) // Coloca un color de fondo por defecto
            .clickable { ShowAct() }
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp),
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