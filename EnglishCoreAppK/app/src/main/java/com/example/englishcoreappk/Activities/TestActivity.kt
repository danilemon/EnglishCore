package com.example.englishcoreappk.Activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme
import com.example.englishcoreappk.Retrofit.Activity
import com.example.englishcoreappk.Retrofit.ClosedQuestion
import com.example.englishcoreappk.Retrofit.CompleteText
import com.example.englishcoreappk.Retrofit.OpenQuestion

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContent {
            EnglishCoreAppKTheme {
                /*val CompleteText= CompleteText(3,"Complete the folowing text","use the words in the box","","Yesterday i was {} in the park , the i {} my friend and we decided to have a {} Runing Competition",listOf<String>("A","A","A","A"),
                    listOf("")
                )*/
                var OpenQ= OpenQuestion(1,"What is the meaning of the word","Run","","")
                var ClosedQuestion= ClosedQuestion(2,"What is the meaining of this word","Run","", listOf<String>("Correr","Volar","nadar","Comer","Hablar"),"Correr")
                var CompleteText= CompleteText(3,"Complete the folowing text","use the words in the box","","Yesterday i was {} in the park , the i {} my friend and we decided to have a {} Runing Competition",listOf<String>("A","A","A","A"),
                    listOf("")
                )
                var Act= Activity("lol","Actividad 1",1,"Verbos", listOf(OpenQ,ClosedQuestion,CompleteText))
                ActivityWraper(Act)
            }
        }
    }
}

