package com.example.englishcoreappk.Activities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.englishcoreappk.Helpers.SimpleDialog
import com.example.englishcoreappk.Retrofit.Activity
import com.example.englishcoreappk.Retrofit.ActivityRepository
import com.example.englishcoreappk.Retrofit.AnsweredActivity
import com.example.englishcoreappk.Retrofit.Answers
import com.example.englishcoreappk.Retrofit.Groups

@Composable
fun AsignedActivities(Group: Groups){

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

@Composable
fun StudentAsignedActivities(GroupID: String){
    var ACT by remember{ mutableStateOf<Activity?>(null)}
    var ShowAct by remember { mutableStateOf(false) }

    var Finished by remember { mutableStateOf(false) }
    var Answers by remember { mutableStateOf<AnswersPkt?>(null) }


    var ShowDialog by remember { mutableStateOf(false) }
    var DialogText by remember { mutableStateOf("") }



    Box(modifier = Modifier.fillMaxSize()){
        if(ShowDialog){
            SimpleDialog(DialogText)
        }
        if (!ShowAct) {
            AsignedActsView(GroupID, false, true) { Act: String ->
                var HasAnsers:(String)-> Unit={S->
                    DialogText=S
                    ShowDialog=true
                }
                ActivityRepository.GetActivityInfo(Act,GroupID,HasAnsers){it->
                    ACT=it
                    ShowAct=true
                }
            }
        }
        else if(Finished){
            ResultsView(Answers!!.Activity,Answers!!.Answer,Answers!!.Respuestas,GroupID){
                ShowAct=false
            }
        }
        else {
            val AsignedAct = ACT!!
            ActivityWraper(AsignedAct){it->
                Finished=true
                Answers=it
            }
        }
    }
}