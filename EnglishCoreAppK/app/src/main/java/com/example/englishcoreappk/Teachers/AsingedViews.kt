package com.example.englishcoreappk.Teachers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.englishcoreappk.Activities.ActivityWraperProfesor
import com.example.englishcoreappk.Activities.AsignedActsView
import com.example.englishcoreappk.Retrofit.ActivityRepository
import com.example.englishcoreappk.Retrofit.AnsweredActivity
import com.example.englishcoreappk.Retrofit.Answers
import com.example.englishcoreappk.Retrofit.Groups

@Composable
fun AsignedActivities(Group: Groups,navController: NavHostController){

    var ACT by remember{ mutableStateOf<AnsweredActivity?>(null)}
    var ShowAct by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize())
    {
        if (!ShowAct) {
            AsignedActsView(Group.ID!!, false, true) { Act: String ->
                ActivityRepository.GetAnsweredActivity(Group.ID, Act) { it ->
                    ACT = it
                    ShowAct = true
                }
            }
        } else {
            val AsignedAct = ACT!!
            val updatedStudentsAnswers = Answers(AsignedAct.StudentsAnswers)
            ActivityWraperProfesor(AsignedAct.Act,updatedStudentsAnswers){
                ShowAct=false
            }
        }
    }
}