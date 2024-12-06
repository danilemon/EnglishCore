package com.example.englishcoreappk.Activities

import android.R
import androidx.activity.compose.BackHandler
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
fun OpenQuestionViewProfesor(index:Int, Question: OpenQuestion,R:Any,Back:()->Unit){
    BackHandler {
        Back()
    }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top) {

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Top) {
            Text(text = index.toString()+". "+Question.Question,style = TextStyle(fontWeight = FontWeight.ExtraBold,color= Color.Black,fontSize = 25.sp  ))
            Spacer(modifier = Modifier.size(10.dp))
            Box(modifier = Modifier.height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
            if(Question.HelpText.isNotEmpty() && Question.HelpText !=null){
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = Question.helpTextQ, style = TextStyle(color= Color.Gray,fontWeight = FontWeight.Bold,fontSize = 20.sp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            if(Question.Img.isNotEmpty() &&  Question.Img!=null){
                Spacer(modifier = Modifier.size(10.dp))
                AsyncImage(
                    model = Question.Img,
                    contentDescription = "Imagen desde URL",
                    modifier = Modifier.size(100.dp).weight(1f) // Tamaño ajustable
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Box(modifier = Modifier.weight(1.5f).fillMaxWidth()){
                    var Answer = ""
                    if (R is String){
                        Answer = R
                    }
                    OpenQuestionView(Answer)
        }
    }
}

@Composable
fun ClosedQuestionQuestionViewProfesor(index:Int, QuestionObj: ClosedQuestion,R:Any,Back:()->Unit){
    var Question= ClosedQuestion(Type =QuestionObj.Type, Question=QuestionObj.Question, HelpText = QuestionObj.HelpText, Img = QuestionObj.Img, Options = QuestionObj.Options, Answer = QuestionObj.Answer )
    BackHandler {
        Back()
    }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top) {

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Top) {
            Text(text = index.toString()+". "+Question.Question,style = TextStyle(fontWeight = FontWeight.ExtraBold,color= Color.Black,fontSize = 25.sp  ))
            Spacer(modifier = Modifier.size(10.dp))
            Box(modifier = Modifier.height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
            if(Question.HelpText.isNotEmpty() && Question.HelpText !=null){
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = Question.helpTextQ, style = TextStyle(color= Color.Gray,fontWeight = FontWeight.Bold,fontSize = 20.sp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            if(Question.Img.isNotEmpty() &&  Question.Img!=null){
                Spacer(modifier = Modifier.size(10.dp))
                AsyncImage(
                    model = Question.Img,
                    contentDescription = "Imagen desde URL",
                    modifier = Modifier.size(100.dp).weight(1f) // Tamaño ajustable
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Box(modifier = Modifier.weight(1.5f).fillMaxWidth()){
            var Answer = 0
            if (R is Int){
                Answer = R
            }
            ClosedQuestionView(Question,Answer)
        }
    }
}

@Composable
fun ClompleteTextQuestionQuestionViewProfesor(index:Int, Question: CompleteText,R:Any,Back:()->Unit){
    BackHandler {
        Back()
    }
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top) {

        Column(modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
            verticalArrangement = Arrangement.Top) {
            Text(text = index.toString()+". "+Question.Question,style = TextStyle(fontWeight = FontWeight.ExtraBold,color= Color.Black,fontSize = 25.sp  ))
            Spacer(modifier = Modifier.size(10.dp))
            Box(modifier = Modifier.height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
            if(Question.HelpText.isNotEmpty() && Question.HelpText !=null){
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = Question.helpTextQ, style = TextStyle(color= Color.Gray,fontWeight = FontWeight.Bold,fontSize = 20.sp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            if(Question.Img.isNotEmpty() &&  Question.Img!=null){
                Spacer(modifier = Modifier.size(10.dp))
                AsyncImage(
                    model = Question.Img,
                    contentDescription = "Imagen desde URL",
                    modifier = Modifier.size(100.dp).weight(1f) // Tamaño ajustable
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
        Box(modifier = Modifier.weight(1.5f).fillMaxWidth()){
            var Answer: List<String> =List(Question.Answers.size){""}
            if(R is List<*>){
                Answer=R as  List<String>
            }
            CompleteTextView(Question,Answer)
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
fun ClosedQuestionView(Question:ClosedQuestion,Answer: Int){

    Box(modifier = Modifier.fillMaxSize()){
        FlowRow(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalArrangement = Arrangement.Center){
            var Selection  = Answer
            for(i in 0..(Question.Options.size)-1){
                var item  = Question.Options[i] // La opción de la lista
                LabeledRadioButon(item, selected = i==Answer)
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
    val TextSegmented = Question.Text.split(" ").filter { it.isNotEmpty() }
    val Answers = MutableList<String>(Question.Answers.size){""}
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
