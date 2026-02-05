package br.com.imcfacil.ui.fluxo

import androidx.lifecycle.ViewModel
import br.com.imcfacil.dominio.caso_de_uso.CalcularIMCUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel responsável por orquestrar
 * todas as etapas do fluxo em etapas.
 */
class ImcViewModel(
    private val CalcularIMCUseCase: CalcularIMCUseCase = CalcularIMCUseCase()
) : ViewModel() {

    private val _estadoUi = MutableStateFlow(EstadoUiImc())
    val estadoUi: StateFlow<EstadoUiImc> = _estadoUi

    fun avancarEtapa() {
        _estadoUi.update {
            it.copy(etapaAtual = it.etapaAtual + 1)
        }
    }

    fun voltarEtapa() {
        _estadoUi.update {
            it.copy(etapaAtual = (it.etapaAtual - 1).coerceAtLeast(0))
        }
    }

    fun aoAlterarPeso(valor: String) {
        _estadoUi.update { it.copy(pesoDigitado = valor) }
    }

    fun aoAlterarAltura(valor: String) {
        _estadoUi.update { it.copy(alturaDigitada = valor) }
    }

    fun calcularResultado() {
        val peso = _estadoUi.value.pesoDigitado.toDoubleOrNull()
        val altura = _estadoUi.value.alturaDigitada.toDoubleOrNull()

        if (peso != null && altura != null) {
            val resultado = CalcularIMCUseCase(peso, altura)
            _estadoUi.update { it.copy(resultadoImc = resultado) }
        }
    }

    fun reiniciarFluxo() {
        _estadoUi.value = EstadoUiImc()
    }
}