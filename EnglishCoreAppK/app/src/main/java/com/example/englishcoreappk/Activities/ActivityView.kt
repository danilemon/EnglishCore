package com.example.englishcoreappk.Activities

import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.englishcoreappk.Retrofit.ClosedQuestion
import com.example.englishcoreappk.Retrofit.CompleteText
import com.example.englishcoreappk.Retrofit.OpenQuestion
import com.example.englishcoreappk.Retrofit.Question

@Composable
fun QuestionView(index:Int,Question: Question){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top) {

    Column(modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
        verticalArrangement = Arrangement.Top) {
        Text(text = index.toString()+". "+Question.questionQ,style = TextStyle(fontWeight = FontWeight.ExtraBold,color=Color.Black,fontSize = 25.sp  ))
        Spacer(modifier = Modifier.size(10.dp))
        Box(modifier = Modifier.height(1.dp)
            .fillMaxWidth()
            .background(Color.Black))
        if(Question.helpTextQ.isNotEmpty()){
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = Question.helpTextQ, style = TextStyle(color=Color.Gray,fontWeight = FontWeight.Bold,fontSize = 20.sp))
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
                is OpenQuestion->{
                    var Answer = remember { mutableStateOf("") }
                    OpenQuestionView(Answer = Answer)
                }
                is ClosedQuestion->{
                    ClosedQuestionView(Question)
                }
                is CompleteText ->{
                    CompleteTextView(Question)
                }
            }
        }

    }
}

@Composable
fun OpenQuestionView(Answer: MutableState<String>){
    Box(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = Answer.value,
            onValueChange = { Answer.value = it },
            modifier = Modifier.fillMaxSize().padding(32.dp)
        )
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ClosedQuestionView(Question:ClosedQuestion){

    Box(modifier = Modifier.fillMaxSize()){
        FlowRow(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top, horizontalArrangement = Arrangement.Center){
            for(i in 0..(Question.options.size)-1){
                            var Selection= remember { mutableStateOf("") }
                            var item=Question.options[i]
                            var Lambda: (String) -> Unit ={ S:String-> Selection.value=S}
                            LabeledRadioButon(item, selected = item==Selection.value, onClick = Lambda)
                        }
            }
        }
    }

@Composable
fun LabeledRadioButon(s: String, selected: Boolean,
                      onClick: ((String) -> Unit),
                      enabled: Boolean = true,){
    Row(
        modifier = Modifier
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(
            selected = selected,
            onClick = {onClick(s)},
            enabled = enabled
        )
        Text(
            text = s,
            modifier = Modifier.padding(start = 8.dp).selectable(selected = selected,
                onClick = {onClick(s)},
                enabled = enabled)
        )

    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun  CompleteTextView(Question:CompleteText){
    val TextSegmented = Question.text.split(" ").filter { it.isNotEmpty() }
    Box(modifier = Modifier.fillMaxSize()) {
        FlowRow(
            modifier = Modifier.fillMaxSize().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.Start

        ) {
            for (i in 0..TextSegmented.size - 1) {

                if (TextSegmented[i] == "{}") {
                    Spiner(Question.options)
                } else {
                    Text(text = TextSegmented[i] + " ")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spiner(Options:List<String>) {

    var expanded by remember { mutableStateOf(false) }
    var Selected by remember { mutableStateOf("Opcion") }

    Box(
        modifier = Modifier
            .width(75.dp)
            .border(1.dp, Color.Gray,) // Borde similar a un Spinner
            .clickable {
                expanded = !expanded
            } // Asegura que el tamaño del menú se ajuste al contenido
    ) {
        // Componente que actúa como el botón desplegable
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = Selected,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f) // Alinea el texto a la izquierda
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = Color.Gray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentWidth()
        ) {
            Options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        Selected = option
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ActivityPreview() {
    EnglishCoreAppKTheme {
        var OpenQ=OpenQuestion(1,"What is the meaning of the word","Run","","")
        var ClosedQuestion= ClosedQuestion(2,"What is the meaining of this word","Run","", listOf<String>("Correr","Volar","nadar","Comer","Hablar"),"Correr")
        var CompleteText=CompleteText(3,"Complete the folowing text","use the words in the box","","Yesterday i was {} in the park , the i {} my friend and we decided to have a {} Runing Competition",listOf<String>("A","A","A","A"),
            listOf("")
        )
        QuestionView(1,CompleteText)
    }
}

