package br.com.imcfacil.ui.componentes

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * Exibe uma imagem central que:
 * - surge invisível
 * - cresce de tamanho
 * - desacelera no final da animação
 *
 * Pensado para telas de onboarding.
 */
@Composable
fun ImagemAnimadaCentral(
    drawableRes: Int,
    tamanhoFinal: Int = 360
) {
    var iniciarAnimacao by remember { mutableStateOf(false) }

    val escala by animateFloatAsState(
        targetValue = if (iniciarAnimacao) 1f else 0.6f,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        ),
        label = "escalaImagem"
    )

    val alpha by animateFloatAsState(
        targetValue = if (iniciarAnimacao) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400
        ),
        label = "alphaImagem"
    )

    LaunchedEffect(Unit) {
        iniciarAnimacao = true
    }

    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        modifier = Modifier
            .size(tamanhoFinal.dp)
            .scale(escala)
            .alpha(alpha)
    )
}