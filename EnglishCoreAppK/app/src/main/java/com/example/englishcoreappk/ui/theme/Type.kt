package com.example.englishcoreappk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.englishcoreappk.R

val GothicFamily = FontFamily(
    Font(R.font.gothica1_bold, FontWeight.Bold),
    Font(R.font.gothica1_thin, FontWeight.Thin),
    Font(R.font.gothica1_black, FontWeight.Black),
    Font(R.font.gothica1_light, FontWeight.Light),
    Font(R.font.gothica1_medium, FontWeight.Medium),
    Font(R.font.gothica1_regular, FontWeight.Normal),
    Font(R.font.gothica1_semibold, FontWeight.SemiBold),
    Font(R.font.gothica1_extrabold, FontWeight.ExtraBold),
    Font(R.font.gothica1_extralight, FontWeight.ExtraLight),
)
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = GothicFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

