package com.example.englishcoreappk.Helpers

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable

@Serializable
data class SpinerItem(
     val Sid: String,
     val Sname: String
)

@Composable
fun Spiner(Options:List<SpinerItem>,Result:(Index: Int)-> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var Selected by remember { mutableStateOf("---") }


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
            if(Options.isNotEmpty()) {
                Options.forEachIndexed { i, option ->
                    DropdownMenuItem(
                        modifier = Modifier.wrapContentWidth().wrapContentHeight(),
                        text = { Text(option.Sname) },
                        onClick = {
                            Selected = option.Sname
                            expanded = false
                            Result(i)
                        }
                    )
                }
            }else{
                Selected="---"
            }
        }
    }
}
