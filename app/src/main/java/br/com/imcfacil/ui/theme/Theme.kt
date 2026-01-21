package br.com.imcfacil.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = VerdeIMC,
    secondary = VerdeClaroIMC,
    error = VermelhoErro
)

private val LightColorScheme = lightColorScheme(
    primary = VerdeIMC,
    secondary = VerdeClaroIMC,
    error = VermelhoErro
)

@Composable
fun IMCFacilTheme(

    // Usei isSystemInDarkTheme para respeitar a preferência do usuário.
    // Isso melhora UX e economiza bateria em telas OLED.
    darkTheme: Boolean = isSystemInDarkTheme(),

    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,

    content: @Composable () -> Unit
) {

    // Pode escolher dinamicamente o esquema de cores.
    // Nenhuma lógica de UI fica espalhada pelas telas.
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    // MaterialTheme centraliza cores, tipografia e shapes.
    // Isso evita hardcode de cores na UI.
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}