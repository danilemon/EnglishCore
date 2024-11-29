package com.example.englishcoreappk.Teachers

import android.widget.Spinner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.Helpers.Spiner
import com.example.englishcoreappk.Helpers.SpinerItem
import com.example.englishcoreappk.Retrofit.ActivityPreview
import com.example.englishcoreappk.Retrofit.ActivityRepository
import com.example.englishcoreappk.Retrofit.ActivityRequest
import com.example.englishcoreappk.Retrofit.Groups
import com.example.englishcoreappk.Retrofit.PracticesPck
import com.example.englishcoreappk.Retrofit.StudentPreview
import com.example.englishcoreappk.Retrofit.Units
import com.example.englishcoreappk.Retrofit.UserData


@Composable
fun assignActivity(Dismiss:()->Unit){
    var isLoading by remember { mutableStateOf(true) }
    var UnitsGroup by remember { mutableStateOf<MutableList<List<Units>>>(mutableListOf()) }

    var SelectedGroup by remember { mutableStateOf< Pair<Groups?,Int>>(Pair(null,0))}
    var SelectedUnit by remember { mutableStateOf<Units?>(null)}
    var SelectedActivity by remember { mutableStateOf<ActivityPreview?>(null)}

    var GroupsItems by remember { mutableStateOf<MutableList<SpinerItem>>(mutableListOf()) }
    var UnitItems by remember { mutableStateOf<MutableList<SpinerItem>>(mutableListOf()) }
    var ActivitiesItems by remember { mutableStateOf<MutableList<SpinerItem>>(mutableListOf()) }
    LaunchedEffect(Unit) {
        GetGroupsData { i->
            isLoading=false
            UnitsGroup=i
            UserData.TeachersGroups.forEach { i->
                GroupsItems.add(SpinerItem(i.ID!!,"${i.Level.toString()} / ${i.Days} / ${i.Hours}"))
            }
        }
    }
    Box(modifier = Modifier.wrapContentHeight()
        .width(400.dp).clip(RoundedCornerShape(16.dp)
            ).background(Color.White)){

        Column(Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFDB162F)), // Fondo de la Box
                contentAlignment = Alignment.Center // Alineación central del contenido
            ) {
                Text(
                    text = "Asignar Tarea",
                    color = Color.White, // Color del texto
                    fontSize = 16.sp, // Tamaño del texto
                    textAlign = TextAlign.Center // Alineación del texto dentro del espacio
                )
            }
            if(isLoading){
                CircularProgressIndicator( modifier = Modifier.align(Alignment.CenterHorizontally))
            }else{
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Grupo",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                )
                Spiner(Options = GroupsItems){Index->
                    SelectedGroup= Pair(UserData.TeachersGroups[Index],Index)
                    SelectedUnit=null
                    SelectedActivity=null
                    UnitItems.clear()
                    UnitsGroup[Index].forEach{i->
                        UnitItems.add(SpinerItem(i.ID,i.Name))
                    }
                    ActivitiesItems.clear()

                }
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Distribuye con espacio entre grupos
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Primer grupo: "Tema" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Tema",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = UnitItems){Index->
                        SelectedUnit=UnitsGroup[SelectedGroup.second][Index]
                        SelectedActivity=null
                        ActivitiesItems.clear()
                        SelectedUnit!!.Activities.forEach{i->
                            ActivitiesItems.add(SpinerItem(i.ID,i.Name))
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el primer grupo y el segundo

                // Segundo grupo: "Tarea" y Spinner
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(
                        text = "Tarea",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                    )
                    Spiner(Options = ActivitiesItems){Index->
                        SelectedActivity=SelectedUnit!!.Activities[Index]
                    }
                }
            }


            Button(modifier = Modifier.fillMaxWidth()
                .padding(20.dp)
                .height(50.dp), onClick = {Dismiss()},colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFDB162F)),) {
                Text(
                    text = "Guardar",
                    color = Color.White // Color del texto
                )
            }
        }
        }
    }
}


@Composable
fun assignExam(Dismiss:()->Unit){
    var isLoading by remember { mutableStateOf(true) }
    var ExamsList by remember { mutableStateOf<MutableList<List<ActivityPreview>>>(mutableListOf())}

    var SelectedGroup by remember { mutableStateOf< Pair<Groups?,Int>>(Pair(null,0))}
    var SelectedExam by remember { mutableStateOf<ActivityPreview?>(null)}

    var GroupsItems by remember { mutableStateOf<MutableList<SpinerItem>>(mutableListOf()) }
    var ExamItems by remember { mutableStateOf< MutableList<SpinerItem>>(mutableListOf())}

    LaunchedEffect(Unit) {
        GetExams { i->
            isLoading=false
            ExamsList=i
            UserData.TeachersGroups.forEach { i->
                GroupsItems.add(SpinerItem(i.ID!!,"${i.Level.toString()} / ${i.Days} / ${i.Hours}"))
            }
        }
    }
    Box(modifier = Modifier.wrapContentHeight()
        .width(400.dp).clip(RoundedCornerShape(16.dp)).background(Color.White)){
        Column(Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF3A6EA5)), // Fondo de la Box
                contentAlignment = Alignment.Center // Alineación central del contenido
            ) {
                Text(
                    text = "Asignar Examen",
                    color = Color.White, // Color del texto
                    fontSize = 16.sp, // Tamaño del texto
                    textAlign = TextAlign.Center // Alineación del texto dentro del espacio
                )
            }
            if(!isLoading) {
                Spacer(Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Distribuye con espacio entre grupos
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Primer grupo: "Tema" y Spinner
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Grupo",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 16.dp) // Espacio entre texto y Spinner
                        )
                        Spiner(GroupsItems){Index->
                            SelectedGroup= Pair(UserData.TeachersGroups[Index],Index)
                            SelectedExam=null
                            ExamItems.clear()
                            ExamsList[Index].forEach{i->
                                ExamItems.add(SpinerItem(i.ID,i.Name))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el primer grupo y el segundo

                    // Segundo grupo: "Tarea" y Spinner
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Examen",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 16.dp) // Espacio entre texto y Spinner
                        )
                        Spiner(ExamItems){Index->
                            SelectedExam=ExamsList[SelectedGroup.second][Index]
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))
                var Min by remember { mutableStateOf(60) }
                var Tries by remember { mutableStateOf(1) }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(modifier = Modifier
                        .height(80.dp).weight(1f).padding(10.dp),
                        value = Min.toString(),
                        onValueChange = { i -> Min = i.toInt() },
                        label = { Text("Minutos") },
                        singleLine = true
                    )
                    TextField(modifier = Modifier
                        .height(80.dp).weight(1f).padding(10.dp),
                        value = Tries.toString(),
                        onValueChange = { i -> Tries = i.toInt() },
                        label = { Text("Intentos") },
                        singleLine = true
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth(0.5f)
                        .padding(20.dp)
                        .height(50.dp).align(Alignment.CenterHorizontally),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3A6EA5)
                    ),
                ) {
                    Text(
                        text = "Fecha",
                        color = Color.White // Color del texto
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp)
                        .height(50.dp),
                    onClick = { Dismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3A6EA5)
                    ),
                ) {
                    Text(
                        text = "Guardar",
                        color = Color.White // Color del texto
                    )
                }
            }else{
                CircularProgressIndicator( modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun assignPractice(Dismiss:()->Unit){
    var isLoading by remember { mutableStateOf(true) }
    var Practices by remember { mutableStateOf<PracticesPck?>(null) }

    var SelectedStudent by remember {mutableStateOf<StudentPreview?>(null)}
    var SelectedPractice by remember{mutableStateOf<ActivityPreview?>(null)}

    var StudentsItems by remember {mutableStateOf<MutableList<SpinerItem>>(mutableListOf())}
    var PracticesItems by remember {mutableStateOf<MutableList<SpinerItem>>(mutableListOf())}
    LaunchedEffect(Unit) {
        ActivityRepository.GetPractices {it->
            Practices=it
            Practices!!.Students.forEach{i->
                StudentsItems.add(SpinerItem(i.ID,i.Name))
            }
            Practices!!.Practices.forEach{i->
                PracticesItems.add(SpinerItem(i.ID,i.Name))
            }
            isLoading=false
        }
    }
    Box(modifier = Modifier.wrapContentHeight()
        .width(400.dp).clip(RoundedCornerShape(16.dp)).background(Color.White)){
        Column(Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Top) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFF74007A)), // Fondo de la Box
                contentAlignment = Alignment.Center // Alineación central del contenido
            ) {
                Text(
                    text = "Asignar Practica",
                    color = Color.White, // Color del texto
                    fontSize = 16.sp, // Tamaño del texto
                    textAlign = TextAlign.Center // Alineación del texto dentro del espacio
                )
            }
            if(!isLoading) {
                Spacer(Modifier.height(15.dp))
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween, // Distribuye con espacio entre grupos
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Primer grupo: "Tema" y Spinner
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Alumno",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                        )
                        Spiner(StudentsItems){i->
                            SelectedStudent=Practices!!.Students[i]
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp)) // Espacio entre el primer grupo y el segundo

                    // Segundo grupo: "Tarea" y Spinner
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text(
                            text = "Practica",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 8.dp) // Espacio entre texto y Spinner
                        )
                        Spiner(PracticesItems){i->
                            SelectedPractice=Practices!!.Practices[i]
                        }
                    }
                }
                Spacer(Modifier.height(15.dp))

                Button(
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp)
                        .height(50.dp),
                    onClick = { Dismiss() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF74007A)
                    ),
                ) {
                    Text(
                        text = "Guardar",
                        color = Color.White // Color del texto
                    )
                }
            }else{
                CircularProgressIndicator( modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}




fun GetGroupsData(callback: (MutableList<List<Units>>)-> Unit){
    var Groups = UserData.TeachersGroups
    var UnitsList: MutableList<List<Units>> = mutableListOf()
    var GroupsIds: MutableList<ActivityRequest> =mutableListOf()
    Groups.forEach{i->
        GroupsIds.add(ActivityRequest(i.ID!!))
    }
    ActivityRepository.GetGroupActss(GroupsIds){i:MutableList<List<Units>> ->
        UnitsList=i
        callback(UnitsList)
    }

}

fun GetExams(callback: (MutableList<List<ActivityPreview>>)-> Unit){
    var Groups = UserData.TeachersGroups
    var GroupExams:MutableList<List<ActivityPreview>> = mutableListOf()
    var GroupsIds: MutableList<ActivityRequest> =mutableListOf()
    Groups.forEach{i->
        GroupsIds.add(ActivityRequest(i.ID!!))
    }
    ActivityRepository.GetGroupExms(GroupsIds){i:MutableList<List<ActivityPreview>> ->
        GroupExams=i
        callback(GroupExams)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAct(){
    assignActivity(){

    }
}