package br.com.imcfacil.ui.componentes

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

/**
 * Indicador de progresso customizado.
 *
 * Nesta fase ele é simples (linha animada),
 * mas já preparado para evoluir para efeito "wavy".
 */
@Composable
fun IndicadorProgressoOndulado(
    progresso: Float
) {
    val progressoAnimado by animateFloatAsState(
        targetValue = progresso,
        animationSpec = tween(
            durationMillis = 500,
            easing = FastOutSlowInEasing
        ),
        label = "progresso"
    )

    // ✅ Lemos o valor composable ANTES do Canvas
    val corProgresso = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
    ) {
        val largura = size.width
        val altura = size.height / 2

        drawLine(
            color = corProgresso,
            start = Offset(0f, altura),
            end = Offset(largura * progressoAnimado, altura),
            strokeWidth = size.height,
            cap = StrokeCap.Round
        )
    }
}