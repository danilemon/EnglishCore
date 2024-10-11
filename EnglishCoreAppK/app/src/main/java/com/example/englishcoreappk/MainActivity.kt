package com.example.englishcoreappk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishCoreAppKTheme {
                Welcome()
            }
        }
    }
}

@Composable
fun Welcome() {
    val expanded = remember {
        mutableStateOf(false)
    }
    val extraPadding = if (expanded.value) 70.dp else 16.dp
    val backgroundAlphaChange = if (expanded.value) 0.5f else 0.0f
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.libertystatue),
            contentDescription = null,
            contentScale = ContentScale.Crop, // Esto ajusta la imagen al tamaño de la pantalla
            modifier = Modifier.fillMaxSize()
        )

        // Capa de oscurecimiento encima de la imagen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backgroundAlphaChange))
        )

        // Contenido superpuesto (Card con botón)
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(extraPadding),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column (

            )
                {
                    OutlinedButton(
                    onClick = { expanded.value = !expanded.value} )
                        {
                            Text(if (expanded.value) "Volver" else "Iniciar sesión")
//                            colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Transparent, // Fondo transparente
//                            contentColor = Color.White          // Color del texto
//                                    ),
//                        shape = RoundedCornerShape(20.dp),
//                        modifier = Modifier.padding(6.dp)
                         }




            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    EnglishCoreAppKTheme {
        Welcome()
    }
}
