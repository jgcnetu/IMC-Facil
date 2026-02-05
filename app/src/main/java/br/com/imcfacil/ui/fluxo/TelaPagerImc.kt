package ui.fluxo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import br.com.imcfacil.ui.componentes.IndicadorProgressoOndulado
import br.com.imcfacil.ui.fluxo.ImcViewModel
import br.com.imcfacil.ui.telas.inicio.TelaInicio

/**
 * Tela raiz do novo fluxo em etapas.
 *
 * Nesta fase, as páginas ainda são vazias.
 */
@Composable
fun TelaPagerImc(
    viewModel: ImcViewModel = viewModel()
) {
    val estadoUi by viewModel.estadoUi.collectAsState()

    val totalPaginas = 4

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { totalPaginas }
    )

    val escopo = rememberCoroutineScope()

    /**
     * Mantém Pager sincronizado com o estado
     */
    LaunchedEffect(estadoUi.etapaAtual) {
        escopo.launch {
            pagerState.animateScrollToPage(estadoUi.etapaAtual)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.height(48.dp))


        IndicadorProgressoOndulado(
            progresso = (pagerState.currentPage + 1) / totalPaginas.toFloat()
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { pagina ->
            when (pagina) {
                0 -> TelaInicio(viewModel)
                else -> Box(modifier = Modifier.fillMaxSize())
            }
        }

    }
}