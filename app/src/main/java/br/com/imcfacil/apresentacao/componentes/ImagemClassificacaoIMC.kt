package br.com.imcfacil.apresentacao.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.imcfacil.ui.util.imagemPorClassificacao

@Composable
fun ImagemClassificacaoIMC(classificacao: String) {

    Image(
        painter = painterResource(imagemPorClassificacao(classificacao)),
        contentDescription = null,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
        modifier = Modifier.size(120.dp)
    )
}