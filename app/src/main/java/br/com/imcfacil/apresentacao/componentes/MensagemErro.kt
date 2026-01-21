package br.com.imcfacil.apresentacao.componentes

import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MensagemErro(mensagem: String?) {

    // Se não houver erro, não renderiza nada
    mensagem?.let {

        AssistChip(
            onClick = {},
            label = { Text(it) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        )
    }
}