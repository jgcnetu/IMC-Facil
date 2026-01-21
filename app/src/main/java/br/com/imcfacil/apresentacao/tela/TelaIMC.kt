package br.com.imcfacil.apresentacao.tela

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.imcfacil.R
import br.com.imcfacil.apresentacao.componentes.BotaoCalcularIMC
import br.com.imcfacil.apresentacao.componentes.CampoAltura
import br.com.imcfacil.apresentacao.componentes.CampoPeso
import br.com.imcfacil.apresentacao.componentes.CardResultadoIMC
import br.com.imcfacil.apresentacao.componentes.MensagemErro
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
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de entrada do peso
        CampoPeso(
            valor = estado.peso,
            aoAlterar = aoAlterarPeso
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada da altura
        CampoAltura(
            valor = estado.altura,
            aoAlterar = aoAlterarAltura
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de ação principal
        BotaoCalcularIMC(
            habilitado = estado.podeCalcular,
            temResultado = estado.temResultado,
            aoClicar = aoClicarCalcular
        )

        // Separador
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )

        // Exibição condicional de erro
        MensagemErro(estado.erro)

        // Exibição condicional do resultado
        CardResultadoIMC(
            resultado = estado.resultado,
            classificacao = estado.classificacao
        )
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