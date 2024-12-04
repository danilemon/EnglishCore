package com.example.englishcoreappk.Teachers

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.englishcoreappk.Retrofit.Groups
import com.example.englishcoreappk.Retrofit.StudentPreview
import com.example.englishcoreappk.Retrofit.TeacherRepository
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.englishcoreappk.Activities.AsignedActsView
import com.example.englishcoreappk.Retrofit.StudentInfo
import com.example.englishcoreappk.Retrofit.UserData

class GroupViewModel:ViewModel(){
    var CurrentGroup by mutableStateOf<Groups?>(null)
        private set

    fun setGroup(Group: Groups){
        CurrentGroup = Group
    }

    fun GetGroup(): Groups? {
        return CurrentGroup
    }
}
@Composable
fun GroupsNavigationHost(navController: NavHostController,Group: Groups){
    NavHost(navController,startDestination="Menu"){
        composable("Menu") {
            GroupMenu(Group,navController)
        }
        composable("StudentsList"){
            GroupList(Group,navController)
        }
        composable("StudentDetails/{id}"){b->
            val idString = b.arguments?.getString("id") // Obtén el ID como String
            val id = idString?.toString() // Convierte a Int, maneja el caso de null
                ShowStudentInfo(id!!, Group)
        }
        composable("Attendance"){
            AttendanceList(Group)
        }
        composable("AsignedActivities"){
            AsignedActsView(Group.ID!!,false,true)
        }
        composable("AsignedExams"){
            AsignedActsView(Group.ID!!,true,false)
        }
    }


}

@Composable
fun GroupsScreen(navController: NavController) {


    val groupsState = remember { mutableStateOf<List<Groups>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) } // Estado para cargar
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Llama a GetGroupsRequest cuando se compone el Composable
        LaunchedEffect(Unit) {
            TeacherRepository.GetGroupsRequest(UserData.User) { groups ->
                groupsState.value = groups // Actualiza el estado con los grupos obtenidos
                isLoading.value = false // Cambia el estado de carga
                UserData.SetupGroups(groups)
            }
        }

        // Muestra un indicador de carga mientras se obtienen los datos
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center) )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                itemsIndexed(groupsState.value) { index, group ->
                    GroupItem(group, (index+1) % 4){
                        navController.navigate(group)
                    } // Asegúrate de que GroupItem tenga los parámetros correctos
                }
            }
        }
    }
}

@Composable
fun GroupItem(Group: Groups,BGcolor:Int,GroupMenu:()->Unit){
    val ColorMap= mapOf(
        0 to Color(0xFFDB162F),
        1 to Color(0xFF2F2A50),
        2 to Color(0xFFC4C6E7),
        3 to Color(0XFF3A6EA5)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { GroupMenu() }
        ,
        colors = CardDefaults.cardColors(
            containerColor = ColorMap[BGcolor] ?: Color.White  // Cambia el color de fondo aquí
        ),



        ) {
        Column(
            modifier = Modifier.padding(16.dp)

        ) {
            Text(text = "Grupo:\n nivel ${Group.Level.toString()} / ${Group.Days} / ${Group.Hours}")
        }
    }


}

@Composable
fun GroupMenu(Group: Groups,navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

            Text(text = "Nivel - ${Group?.Level}",
                style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${Group?.Days} / ${Group?.Hours}"
            )

        Column(
            modifier = Modifier.fillMaxSize(), // Ocupa todo el tamaño disponible
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Top // Comienza desde la parte superior
        ) {
            // Primer Box
            Box(
                modifier = Modifier
                    .weight(1f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xffDB162F))
                    .clickable {
                        navController.navigate("StudentsList")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Lista de alumnos", color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }//Attendance
            // Segundo Box
            Box(
                modifier = Modifier
                    .weight(1f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xffC4C6E7))
                    .clickable {
                        navController.navigate("Attendance")
                    }
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Tomar Asistencia",color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }

            // Tercer Box
            Box(
                modifier = Modifier
                    .weight(0.5f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xff3A6EA5))
                    .clickable {
                        navController.navigate("AsignedActivities")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Tareas", color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }
            Box(
                modifier = Modifier
                    .weight(0.5f) // Comparte espacio con otros Boxes
                    .fillMaxWidth() // Asegura que el Box ocupe todo el ancho
                    .padding(10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xff3A6EA5))
                    .clickable{
                        navController.navigate("AsignedExams")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Examenes", color = Color.Black,style = TextStyle(
                    fontSize = 25.sp, // Cambiar tamaño a 20sp
                    fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
                ))
            }
        }
    }
}

@Composable
fun GroupList(Group: Groups,navController: NavController){


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = "Nivel - ${Group?.Level}",
            style = TextStyle(
                fontSize = 25.sp, // Cambiar tamaño a 20sp
                fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${Group?.Days} / ${Group?.Hours}"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            val isLoading = remember { mutableStateOf(true) }
            val studentsList= remember { mutableStateOf<List<StudentPreview>>(emptyList())}
            LaunchedEffect(Unit) {
                TeacherRepository.GetGroupStudets(Group?.ID!!) { Students ->
                    studentsList.value = Students // Actualiza el estado con los grupos obtenidos
                    isLoading.value = false // Cambia el estado de carga
                }
            }
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center) )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(studentsList.value){student ->
                        ListItem(student){
                            navController.navigate("StudentDetails/${student.ID}")
                        }
                    }
                }
            }
        }
        }

}

@Composable
fun ListItem(Student:StudentPreview,StudentInfo:()->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp), // Aumenté la altura para mejor visualización

        colors = CardDefaults.cardColors(
            containerColor = Color(0xff306388)  // Cambia el color de fondo aquí
        )
    ) {
        Box(
            Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                text = Student.Name,
                style = TextStyle(color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(20.dp) // Tamaño del círculo

            ) {
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        StudentInfo()
                    }) {
                    drawCircle(
                        color = Color.Black, // Color del borde
                        radius = size.minDimension / 2, // Radio del círculo
                        style = Stroke(width = 2f) // Ancho del borde
                    )
                }
            }
        }
    }

}

@Composable
fun ShowStudentInfo(StudentID: String,Group: Groups){
    val isLoading = remember { mutableStateOf(true) }
    val Student = remember{ mutableStateOf<StudentInfo?>(null) }
        //remember {mutableStateOf<StudentInfo?>(null)}
    Box(modifier = Modifier.fillMaxSize() ){
        LaunchedEffect(Unit) {
            TeacherRepository.GetStudentInfo(StudentID) { Students ->
                Student.value = Students // Actualiza el estado con los grupos obtenidos
                isLoading.value = false // Cambia el estado de carga
            }
        }
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center) )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(Student.value?.Name ?: "",modifier = Modifier.weight(1f))
                    Spacer(Modifier.height(20.dp))
                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .weight(1f)){
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp),modifier = Modifier.weight(1f)) {
                            Text("Telefono:")
                            Text(Student.value?.Cellphone?:"")
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp),modifier = Modifier.weight(1f)) {
                            Text("Direcion:")
                            Text(Student.value?.Adrress?:"")
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Correo Electronico: Danylop07@gmail.com",modifier = Modifier.weight(1f))
                    Spacer(Modifier.height(20.dp))
                    Text("Informacion del grupo",modifier = Modifier.weight(1f))
                    Spacer(Modifier.height(20.dp))
                    Row(modifier= Modifier
                        .fillMaxWidth()
                        .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)){

                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                append("Nivel: ")
                            }
                            append(Group.Level.toString())
                        },modifier = Modifier.weight(1f))

                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                append("Dias: ")
                            }
                            append(Group.Days)
                        },modifier = Modifier.weight(1f))}
                        Spacer(Modifier.height(20.dp))
                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                                append("Horario: ")
                            }
                            append(Group.Hours)
                        },modifier = Modifier.weight(1f))





                }

            }
        }

    }

@Composable
fun AttendanceList(Group: Groups){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Text(text = "Nivel - ${Group?.Level}",
            style = TextStyle(
                fontSize = 25.sp, // Cambiar tamaño a 20sp
                fontWeight = FontWeight.ExtraBold // Cambiar grosor a Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${Group?.Days} / ${Group?.Hours}"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            val isLoading = remember { mutableStateOf(true) }
            val studentsList= remember { mutableStateOf<List<StudentPreview>>(emptyList())}
            LaunchedEffect(Unit) {
                TeacherRepository.GetGroupStudets(Group?.ID!!) { Students ->
                    studentsList.value = Students // Actualiza el estado con los grupos obtenidos
                    isLoading.value = false // Cambia el estado de carga
                }
            }
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center) )
            } else {
                var StudentAtendance by remember{ mutableStateOf(MutableList(studentsList.value.size){false}) }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(studentsList.value){i,student ->
                        AttendanceItem(student){B:Boolean->
                            StudentAtendance[0]=B
                        }
                    }
                }
                Button(modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(20.dp)
                    .height(50.dp)
                    .align(Alignment.BottomCenter)
                    , onClick = {},) { Text(
                    text = "Guardar",
                    color = Color.White // Color del texto
                )}
            }

        }

    }
}

@Composable
fun AttendanceItem(Student:StudentPreview,Attendance:(Boolean)->Unit){
    var Selected by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(2.dp, Color.Black)
            .background(Color(0xffC4C6E7)) // Aumenté la altura para mejor visualización
    ) {
        Box(
            Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                text = Student.Name,
                style = TextStyle(color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp)
                    .size(20.dp) // Tamaño del círculo

            ) {
                Checkbox(
                    checked = Selected,
                    onCheckedChange = {i->
                        Selected=i
                        Attendance(i) }
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun GroupPreview() {
    var S=StudentPreview("","Daniel Lopez A")
    EnglishCoreAppKTheme {
        AttendanceItem(S){

        }
    }
}