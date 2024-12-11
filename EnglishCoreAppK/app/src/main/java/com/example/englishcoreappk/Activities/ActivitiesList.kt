package com.example.englishcoreappk.Activities

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.englishcoreappk.Helpers.SpinerItem
import com.example.englishcoreappk.Helpers.Spiner
import com.example.englishcoreappk.Retrofit.ActivityRepository
import com.example.englishcoreappk.Retrofit.AsignedView
import com.example.englishcoreappk.Retrofit.UnitViews


@Composable
fun AsignedActsView(ID: String, Exam: Boolean, Act: Boolean, Student: Boolean=false, Next:(Act: String)-> Unit) {

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
                                if(!item.HasAnswers && !Student){
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



