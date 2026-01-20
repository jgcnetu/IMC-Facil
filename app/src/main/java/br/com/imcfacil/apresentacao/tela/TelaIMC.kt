package br.com.imcfacil.apresentacao.tela

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.imcfacil.apresentacao.estado.EstadoIMC
import br.com.imcfacil.apresentacao.viewmodel.IMCViewModel

/** Composable de alto nível da tela de IMC.
 *
 * Responsabilidades:
 * - Obter o ViewModel
 * - Observar o estado
 * - Delegar eventos e dados para a UI pura
 */
@Composable
fun TelaIMC(

    // O ViewModel é obtido via função viewModel()
    // Isso garante:
    // - Reuso da instância
    // - Integração com o lifecycle
    // - Persistência em rotações de tela
    viewModel: IMCViewModel = viewModel()
) {

    // Coleta o StateFlow exposto pelo ViewModel.
    // O `by` + `collectAsState()`:
    // - Converte Flow em State do Compose
    // - Recompõe a UI automaticamente quando mudar
    val estado by viewModel.estado.collectAsState()

    // Deleguei a renderização para um Composable que NÃO conhece ViewModel.
    TelaIMCConteudo(
        estado = estado,

        // Passei apenas funções (eventos), mantendo a UI desacoplada da lógica
        aoAlterarPeso = viewModel::aoAlterarPeso,
        aoAlterarAltura = viewModel::aoAlterarAltura,
        aoClicarCalcular = viewModel::aoClicarCalcular
    )
}

/**
 * Composable responsável APENAS por desenhar a interface.
 *
 * Importante:
 * - Não conhece ViewModel
 * - Não executa lógica
 * - Apenas reflete o estado recebido
 */
@Composable
fun TelaIMCConteudo(
    estado: EstadoIMC,
    aoAlterarPeso: (String) -> Unit,
    aoAlterarAltura: (String) -> Unit,
    aoClicarCalcular: () -> Unit
) {

    // Column organiza os elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Título da tela
        Text(
            text = "IMC Fácil",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de entrada do peso
        OutlinedTextField(
            value = estado.peso, // sempre vem do estado
            onValueChange = aoAlterarPeso, // evento sobe para o ViewModel
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada da altura
        OutlinedTextField(
            value = estado.altura, // sempre vem do estado
            onValueChange = aoAlterarAltura, // evento sobe para o ViewModel
            label = { Text("Altura (m)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de ação principal
        Button(
            onClick = aoClicarCalcular,
            // UI reage ao estado, não decide sozinha
            enabled = estado.podeCalcular,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Calcular IMC")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Exibição condicional de erro
        estado.erro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Exibição condicional do resultado
        estado.resultado?.let {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Resultado: $it",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // Exibição da classificação
        estado.classificacao?.let { textoClassificacao ->
            Text(
                text = textoClassificacao,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTelaIMC() {

    // Estado fake apenas para visualização
    val estadoPreview = EstadoIMC(
        peso = "70",
        altura = "1.75",
        resultado = "22.86",
        classificacao = "Peso normal",
        erro = null
    )

    // Cahmo apenas a UI pura
    TelaIMCConteudo(
        estado = estadoPreview,
        aoAlterarPeso = {},
        aoAlterarAltura = {},
        aoClicarCalcular = {}
    )
}