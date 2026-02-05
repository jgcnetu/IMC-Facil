package br.com.imcfacil.ui.telas.inicio

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.imcfacil.ui.componentes.ImagemAnimadaCentral
import br.com.imcfacil.R
import br.com.imcfacil.ui.fluxo.ImcViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Primeira tela do fluxo.
 * Apresenta o app ao usuário.
 */
@Composable
fun TelaInicio(
    viewModel: ImcViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        ImagemAnimadaCentral(
            drawableRes = R.drawable.ic_imc // ajuste para seu recurso
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bem-vindo ao IMC Fácil",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Descubra seu Índice de Massa Corporal de forma rápida e simples.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.avancarEtapa() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Começar")
        }
    }
}
