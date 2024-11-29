package com.example.englishcoreappk.Activities

import android.annotation.SuppressLint
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.englishcoreappk.R
import com.example.englishcoreappk.Retrofit.Activity
import com.example.englishcoreappk.Retrofit.ClosedQuestion
import com.example.englishcoreappk.Retrofit.CompleteText
import com.example.englishcoreappk.Retrofit.OpenQuestion
import com.example.englishcoreappk.Retrofit.Question
import com.example.englishcoreappk.Retrofit.StudentPreview
import com.example.englishcoreappk.Retrofit.UserAnswer


@Composable
fun ActivityWraper(Activity: Activity){
    var Index = remember { mutableStateOf(0) }
    var Vindex = remember { mutableStateOf(0) }

    var Q by remember { mutableStateOf(Question(1,"","",""))  }
    var navController = rememberNavController()

    val Questions=Activity.Questions
    var QuestionAnswers:MutableList<Any> = mutableListOf()

    Questions.forEach{i->
        when(i){
            is OpenQuestion->{
                var OpQ = i as OpenQuestion
                QuestionAnswers.add(OpQ.answer)
            }
            is ClosedQuestion->{
                var ClQ = i as ClosedQuestion
                QuestionAnswers.add(ClQ.answer)
            }
            is CompleteText ->{
                var CTQ= i as CompleteText
                QuestionAnswers.add(CTQ.answers)
            }
            }
    }
    val UserAnswer by remember { mutableStateOf<MutableList<Any>>( mutableListOf())}
    Questions.forEach{i->
        when(i){
            is OpenQuestion->{
                UserAnswer.add("")
            }
            is ClosedQuestion->{
                UserAnswer.add("")
            }
            is CompleteText ->{
                var L  = mutableListOf<String>()
                UserAnswer.add(L)
            }
        }
    }
    Scaffold(
        topBar = {
         Row(modifier = Modifier.fillMaxWidth()
             .wrapContentHeight(),
             horizontalArrangement = Arrangement.SpaceBetween,
             verticalAlignment = Alignment.CenterVertically){
             Text(text =(Activity.Name+": "+Activity.Topic), modifier = Modifier.align(Alignment.CenterVertically))
             Text(text = (Vindex.value+1).toString()+"/"+Activity.Questions.size.toString(), color = Color.Gray)
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
                    if(Index.value>0){
                        Index.value--
                        Vindex.value--
                        Q=Questions[Index.value]
                        navController.navigate(Q)
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
                IconButton(onClick = { if(Index.value<Questions.size-1){
                    Index.value++
                    Vindex.value++
                    Q=Questions[Index.value]
                    navController.navigate(Q)
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
            NavigationHost(navController,Index,Questions[0],Activity,UserAnswer,QuestionAnswers)
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, Index: MutableState<Int>, StartQuestion:Question, Act:Activity, Answer:MutableList<Any>, Respuestas:MutableList<Any>) {
    var Lambda:(Any)->Unit ={Ans->
        Answer[Index.value]=Ans}
    NavHost(navController, startDestination ="StartQuestion"){
        composable("StartQuestion"){
            QuestionView(Index.value+1,StartQuestion,Lambda,Answer[Index.value])
        }
        composable<OpenQuestion>{backStackEntry ->
            val Question:OpenQuestion=backStackEntry.toRoute()
            QuestionView(Index.value+1,Question,Lambda,Answer[Index.value])
        }
        composable<ClosedQuestion>{backStackEntry ->
            val Question:ClosedQuestion=backStackEntry.toRoute()
            QuestionView(Index.value+1,Question,Lambda,Answer[Index.value])
        }
        composable<CompleteText>{backStackEntry ->
            val Question:CompleteText=backStackEntry.toRoute()
            QuestionView(Index.value+1,Question,Lambda,Answer[Index.value])
        }
        composable("Results"){
            ResultsView(Act,Answer,Respuestas)
        }

    }
}

@Composable
fun ResultsView(Activity: Activity,Answer:MutableList<Any>,Respuestas:MutableList<Any>){
    var Score =  0
    Answer.forEachIndexed{index,S->
        if(S==Respuestas[index]){
            Score++
        }
    }
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
                text = "Total de aciertos: ${Score}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Total de preguntas: ${Activity.Questions.size}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(10.dp))
            var P = ((Score.toFloat()/Answer.size.toFloat())*100)
            Text(
                text = "Calificación final: ${P.toString()}/100",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
        Button(
            onClick = { /* Acción del botón */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E2D7C)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text(text = "Ok", color = Color.White)
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActivityWraperProfesor(Activity: Activity,Student:StudentPreview,Score: Float,UsrAnswers: MutableList<UserAnswer>){
    var Index = remember { mutableStateOf(0) }
    //

    var FSocore by remember { mutableStateOf(Score) }
    var CorrectAnswers by remember {mutableStateOf(0)}

    UsrAnswers.forEach{i->
        if(i.Correct){
           CorrectAnswers++
        }
    }
    Text(text = "Aciertos:${CorrectAnswers}")

    var Q by remember { mutableStateOf(Question(1,"","",""))  }
    var navController = rememberNavController()

    val Questions=Activity.Questions
   var QuestionAnswers:MutableList<Any> = mutableListOf()

    Questions.forEach{i->
        when(i){
            is OpenQuestion->{
                var OpQ = i as OpenQuestion
                QuestionAnswers.add(OpQ.answer)
            }
            is ClosedQuestion->{
                var ClQ = i as ClosedQuestion
                QuestionAnswers.add(ClQ.answer)
            }
            is CompleteText ->{
                var CTQ= i as CompleteText
                QuestionAnswers.add(CTQ.answers)
            }
        }
    }
    val UserAnswer by remember { mutableStateOf<MutableList<Any>>( mutableListOf())}
    Questions.forEach{i->
        when(i){
            is OpenQuestion->{
                UserAnswer.add("")
            }
            is ClosedQuestion->{
                UserAnswer.add("")
            }
            is CompleteText ->{
                var L  = mutableListOf<String>()
                UserAnswer.add(L)
            }
        }
    }
    Scaffold(
        topBar = {
            Column(modifier=Modifier.wrapContentHeight().fillMaxWidth(), verticalArrangement = Arrangement.Top) {
            Text(text = Student.Name, style = TextStyle(color=Color.Black), fontWeight = FontWeight.Bold)
            Row (modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(text = "Calificacion:${FSocore}")
                Text(text = "Aciertos:${CorrectAnswers}")

            }
            Row(modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(text =(Activity.Name+": "+Activity.Topic), modifier = Modifier.align(Alignment.CenterVertically))
                Text(text = (Index.value+1).toString()+"/"+Activity.Questions.size.toString(), color = Color.Gray)
            }
            }
        },
        bottomBar = {

        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            var Correct by remember { mutableStateOf(UsrAnswers[Index.value].Correct) }
            IconButton(onClick = {Correct=!Correct}) {
                if (Correct){
                    Icon(imageVector = ImageVector.vectorResource(id=R.drawable.correct_answer),contentDescription="", tint = Color.Green)
                }else{
                    Icon(imageVector = ImageVector.vectorResource(id=R.drawable.incorrect_answer),contentDescription="", tint = Color.Red)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Flecha Izquierda
                IconButton(onClick = {
                    if (Index.value > 0) {
                        Index.value--
                        Q = Questions[Index.value]
                        navController.navigate(Q)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Flecha Izquierda"
                    )
                }

                // Botón Central
                Button(onClick = { navController.navigate("Results") }) {
                    Text("Guardar Cambios")
                }

                // Flecha Derecha
                IconButton(onClick = {
                    if (Index.value < Questions.size - 1) {
                        Index.value++
                        Q = Questions[Index.value]
                        navController.navigate(Q)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Flecha Derecha"
                    )
                }
            }
        }
        }
    ) {
        paddingValues ->
        // Box que ajusta su tamaño según el espacio disponible entre la topBar y la bottomBar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Usa paddingValues proporcionado por el Scaffold
        ) {
            NavigationHostProfesor(navController,Index,Questions[0],Activity,QuestionAnswers,UsrAnswers)
        }
    }
}

@Composable
fun NavigationHostProfesor(navController: NavHostController, Index: MutableState<Int>, StartQuestion:Question, Act:Activity, Answer:MutableList<Any>, UserAnswers:MutableList<UserAnswer>) {

    NavHost(navController, startDestination ="StartQuestion"){
        composable("StartQuestion"){
            QuestionViewProfesor(Index.value+1,StartQuestion,UserAnswers[Index.value].Answers)
        }
        composable<OpenQuestion>{backStackEntry ->
            val Question:OpenQuestion=backStackEntry.toRoute()
            QuestionViewProfesor(Index.value+1,Question,UserAnswers[Index.value].Answers)
        }
        composable<ClosedQuestion>{backStackEntry ->
            val Question:ClosedQuestion=backStackEntry.toRoute()
            QuestionViewProfesor(Index.value+1,Question,UserAnswers[Index.value].Answers)
        }
        composable<CompleteText>{backStackEntry ->
            val Question:CompleteText=backStackEntry.toRoute()
            QuestionViewProfesor(Index.value+1,Question,UserAnswers[Index.value].Answers)
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