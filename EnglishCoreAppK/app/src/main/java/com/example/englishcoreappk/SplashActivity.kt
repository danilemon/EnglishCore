package com.example.englishcoreappk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.englishcoreappk.ui.theme.EnglishCoreAppKTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EnglishCoreAppKTheme {
                var startAnimation by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    startAnimation = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Finaliza SplashActivity para que no se pueda regresar a ella
                    }, 1500) // Retraso de 3 segundos
                }

                val alpha by animateFloatAsState(
                    targetValue = if (startAnimation) 1f else 0f,
                    animationSpec = tween(durationMillis = 1200) // Duración de la animación de desvanecimiento
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.englishcore_fhd),
                        contentDescription = "Splash Logo",
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .align(Alignment.Center)
                            .graphicsLayer(alpha = alpha) // Aplicar la opacidad animada
                    )
                }
            }
        }
    }
}
