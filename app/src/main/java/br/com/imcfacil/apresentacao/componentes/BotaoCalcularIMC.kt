package br.com.imcfacil.apresentacao.componentes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.imcfacil.R

/**
 * Botão principal da tela de IMC.
 *
 * Decisão de arquitetura:
 * - O botão NÃO decide comportamento
 * - Ele apenas reflete o estado da tela
 */
@Composable
fun BotaoCalcularIMC(
    habilitado: Boolean,
    temResultado: Boolean,
    aoClicar: () -> Unit
) {

    // Define texto do botão conforme estado atual
    val textoBotao = if (temResultado) {
        "CALCULAR NOVAMENTE"
    } else {
        "CALCULAR IMC"
    }

    // Define ícone conforme estado
    val iconeBotao = if (temResultado) {
        R.drawable.iv_reset
    } else {
        R.drawable.iv_calculate
    }

    /**
     * Botão principal.
     *
     * enabled:
     * - Quando NÃO há resultado → depende da validação
     * - Quando há resultado → sempre habilitado para reset
     */
    Button(
        onClick = aoClicar,
        enabled = habilitado || temResultado,
        modifier = Modifier.fillMaxWidth()
    ) {

        Icon(
            painter = painterResource(iconeBotao),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(textoBotao)
    }
}