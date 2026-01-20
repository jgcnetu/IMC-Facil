package br.com.imcfacil.apresentacao.viewmodel

import androidx.lifecycle.ViewModel
import br.com.imcfacil.apresentacao.estado.EstadoIMC
import br.com.imcfacil.dominio.caso_de_uso.CalcularIMCUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** ViewModel da tela de IMC.
 *
 * Responsabilidades:
 * - Controlar o estado da tela
 * - Converter dados da UI
 * - Chamar o UseCase
 *
 * NÃO deve conter lógica de UI (cores, layouts, etc.)
 */
class IMCViewModel: ViewModel() {

    // UseCase pertence ao domínio.
    // Neste momento estou instanciando direto, mas futuramente pode vir por injeção de dependência.
    private val calcularIMCUseCase = CalcularIMCUseCase()

    // Estado mutável interno.
    // Apenas o ViewModel pode modificá-lo.
    private val _estado = MutableStateFlow(EstadoIMC())

    // Estado imutável exposto para a UI.
    val estado: StateFlow<EstadoIMC> = _estado.asStateFlow()

    // Atualiza o peso conforme o usuário digita.
    fun aoAlterarPeso(novoPeso: String) {
        _estado.value = _estado.value.copy(
            peso = novoPeso,
            // limpa erros ao digitar novamente
            erro = null,
        )
    }

    // Atualiza a altura conforme o usuário digita.
    fun aoAlterarAltura(novaAltura: String) {
        _estado.value = _estado.value.copy(
            altura = novaAltura,
            // limpa erros ao digitar novamente
            erro = null,
        )
    }

    // Ação disparada quando o usuário clica em "Calcular".
    fun aoClicarCalcular() {

        // Converte peso e altura para Double.
        val peso = _estado.value.peso.toDoubleOrNull()
        val altura = _estado.value.altura.toDoubleOrNull()

        // Validação básica de entrada.
        if (peso == null || altura == null || peso <= 0 || altura <= 0) {
            _estado.value = _estado.value.copy(
                erro = "Peso e altura devem ser maiores que zero."
            )
            return
        }

        // Executa a regra de negócio chamando o UseCase para calcular o IMC.
        val resultado = calcularIMCUseCase(peso, altura)

        // Atualiza o estado com os dados calculados.
        _estado.value = _estado.value.copy(
            resultado = "%.2f".format(resultado.valor),
            classificacao = resultado.classificacao,
            erro = null
        )
    }
}