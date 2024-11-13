package com.example.englishcoreappk.Activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.englishcoreappk.Retrofit.Activity
import com.example.englishcoreappk.Retrofit.ClosedQuestion
import com.example.englishcoreappk.Retrofit.CompleteText
import com.example.englishcoreappk.Retrofit.OpenQuestion
import com.example.englishcoreappk.Retrofit.Question


@Composable
fun ActivityWraper(Activity: Activity){
    var Index by remember { mutableStateOf(0) }
    var navController = rememberNavController()
    val Questions=Activity.Questions
    Scaffold(
        topBar = {
         Row(modifier = Modifier.fillMaxWidth()
             .wrapContentHeight(),
             horizontalArrangement = Arrangement.SpaceBetween,
             verticalAlignment = Alignment.CenterVertically){
             Text(text =(Activity.Name+": "+Activity.Topic), modifier = Modifier.align(Alignment.CenterVertically))
             Text(text = (Index+1).toString()+"/"+Activity.Questions.size.toString(), color = Color.Gray)
         }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flecha Izquierda
                IconButton(onClick = {
                    if(Index>0){
                        Index--
                        navController.navigate(Questions[Index])
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Flecha Izquierda"
                    )
                }

                // Botón Central
                Button(onClick = { navController.navigate("Results") }) {
                    Text("Botón Central")
                }

                // Flecha Derecha
                IconButton(onClick = { if(Index<Questions.size-1){
                    Index++
                    navController.navigate(Questions[Index])
                }}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Flecha Derecha"
                    )
                }
            }
        }
    ) {paddingValues ->
        // Box que ajusta su tamaño según el espacio disponible entre la topBar y la bottomBar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Usa paddingValues proporcionado por el Scaffold
        ) {
            NavigationHost(navController,Index,Questions[0],Activity)
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController,Index:Int,StartQuestion:Question,Act:Activity) {
    NavHost(navController, startDestination ="StartQuestion"){
        composable("StartQuestion"){
            QuestionView(Index+1,StartQuestion)
        }
        composable<OpenQuestion>{backStackEntry ->
            val Question:OpenQuestion=backStackEntry.toRoute()
            QuestionView(Index+1,Question)
        }
        composable<ClosedQuestion>{backStackEntry ->
            val Question:ClosedQuestion=backStackEntry.toRoute()
            QuestionView(Index+1,Question)
        }
        composable<CompleteText>{backStackEntry ->
            val Question:CompleteText=backStackEntry.toRoute()
            QuestionView(Index+1,Question)
        }
        composable("Results"){
            ResultsView(Act)
        }

    }
}

@Composable
fun ResultsView(Activity: Activity){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${Activity.Name}:${Activity.Topic}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier.height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
            Text(
                text = "Total de aciertos:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Total de preguntas:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Calificación final",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(20.dp))
            Button(
                onClick = { /* Acción del botón */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2D7C)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Ok", color = Color.White)
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun WraperPreview(){
    var OpenQ= OpenQuestion(1,"What is the meaning of the word","Run","","")
    var ClosedQuestion= ClosedQuestion(2,"What is the meaining of this word","Run","", listOf<String>("Correr","Volar","nadar","Comer","Hablar"),"Correr")
    var CompleteText= CompleteText(3,"Complete the folowing text","use the words in the box","","Yesterday i was {} in the park , the i {} my friend and we decided to have a {} Runing Competition",listOf<String>("A","A","A","A"),
        listOf("")
    )
    var Act=Activity("lol","Actividad 1",1,"Verbos", listOf(OpenQ,ClosedQuestion,CompleteText))
   // ActivityWraper(Act)
}