package com.example.englishcoreappk.Activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.englishcoreappk.Retrofit.ClosedQuestion
import com.example.englishcoreappk.Retrofit.CompleteText
import com.example.englishcoreappk.Retrofit.OpenQuestion
import com.example.englishcoreappk.Retrofit.Question
import kotlin.String
import kotlin.collections.List

@Composable
fun QuestionViewProfesor(index:Int, Question: Question,R:Any){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top) {

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Top) {
            Text(text = index.toString()+". "+Question.questionQ,style = TextStyle(fontWeight = FontWeight.ExtraBold,color= Color.Black,fontSize = 25.sp  ))
            Spacer(modifier = Modifier.size(10.dp))
            Box(modifier = Modifier.height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
            if(Question.helpTextQ.isNotEmpty()){
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = Question.helpTextQ, style = TextStyle(color= Color.Gray,fontWeight = FontWeight.Bold,fontSize = 20.sp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            if(Question.imgQ.isNotEmpty()){
                Spacer(modifier = Modifier.size(10.dp))
                AsyncImage(
                    model = Question.imgQ,
                    contentDescription = "Imagen desde URL",
                    modifier = Modifier.size(100.dp).weight(1f) // Tamaño ajustable
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Box(modifier = Modifier.weight(1.5f).fillMaxWidth()){
            when(Question){
                is OpenQuestion ->{
                    var Answer = ""
                    if (R is String){
                        Answer = R
                    }
                    OpenQuestionView(Answer)
                }
                is ClosedQuestion ->{
                    var Answer = ""
                    if (R is String){
                        Answer = R
                    }
                    ClosedQuestionView(Question,Answer)
                }
                is CompleteText ->{
                    var Answer: List<String> =List(Question.answers.size){""}
                    if(R is List<*>){
                        Answer=R as  List<String>
                    }
                    CompleteTextView(Question,Answer)
                }
            }
        }

    }
}
@Composable
fun OpenQuestionView(S:String){
    Box(modifier = Modifier.fillMaxSize()) {
       Text(modifier = Modifier
           .wrapContentSize().align(Alignment.Center),text=S)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClosedQuestionView(Question:ClosedQuestion,Answer:String){

    Box(modifier = Modifier.fillMaxSize()){
        FlowRow(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalArrangement = Arrangement.Center){
            var Selection  = Answer
            for(i in 0..(Question.options.size)-1){
                var item  = Question.options[i] // La opción de la lista
                LabeledRadioButon(item, selected = item==Selection)
            }
        }
    }
}

@Composable
fun LabeledRadioButon(s: String, selected: Boolean,
                      enabled: Boolean=false ){
    Row(
        modifier = Modifier
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = selected,
            onClick = {

            },
            enabled = enabled
        )
        Text(
            text = s,
            modifier = Modifier.padding(start = 8.dp).selectable(selected = selected,
                onClick = {},
                enabled = enabled)
        )

    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun  CompleteTextView(Question:CompleteText,Answwers:List<String>){
    val TextSegmented = Question.text.split(" ").filter { it.isNotEmpty() }
    val Answers = MutableList<String>(Question.answers.size){""}
    var Counter = 0
    Box(modifier = Modifier.fillMaxSize()) {
        FlowRow(
            modifier = Modifier.fillMaxSize().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.Start

        ) {
            for (i in 0..TextSegmented.size - 1) {

                if (TextSegmented[i] == "{}") {
                    Text(
                        text = Answwers[Counter],
                        style = TextStyle(
                            fontWeight = FontWeight.Bold, // Negritas
                        )
                    )
                    Counter++
                    /*Spiner(Question.options,Counter,Saved.getOrElse(Counter){""}){S,I->
                        Answers[I]=S
                        answer(Answers)
                    }
                    */
                } else {
                    Text(text = TextSegmented[i] + " ")
                }
            }
        }
    }
}
