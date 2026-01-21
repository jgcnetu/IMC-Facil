package br.com.imcfacil.apresentacao.viewmodel

import androidx.lifecycle.ViewModel
import br.com.imcfacil.apresentacao.estado.EstadoIMC
import br.com.imcfacil.dominio.caso_de_uso.CalcularIMCUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    /**
     * Normaliza números digitados pelo usuário.
     *
     * Decisão:
     * - Usuários brasileiros usam vírgula como separador decimal
     * - Kotlin espera ponto
     * - Esta é uma REGRA DE DOMÍNIO, não de UI
     */
    private fun normalizarNumero(valor: String): String {
        return valor.replace(",", ".")
    }

    // Atualiza o peso conforme o usuário digita.
    fun aoAlterarPeso(valor: String) {
        val pesoNormalizado = normalizarNumero(valor)

        _estado.update {
            it.copy(
                peso = pesoNormalizado,

                // Estado derivado: define se pode calcular
                podeCalcular = pesoNormalizado.isNotBlank() && it.altura.isNotBlank(),

                // Sempre limpamos erro ao editar
                erro = null
            )
        }
    }

    // Atualiza a altura conforme o usuário digita.
    fun aoAlterarAltura(valor: String) {
        val alturaNormalizada = normalizarNumero(valor)

        _estado.update {
            it.copy(
                altura = alturaNormalizada,
                podeCalcular = it.peso.isNotBlank() && alturaNormalizada.isNotBlank(),
                erro = null
            )
        }
    }

    /**
     * Ação principal do botão.
     *
     * Importante:
     * - O MESMO botão serve para calcular e resetar
     * - O comportamento depende do estado atual
     */
    fun aoClicarCalcular() {

        // Se já existe resultado, o botão significa "Calcular novamente"
        if (_estado.value.temResultado) {
            resetarEstado()
            return
        }

        // Converte peso e altura para Double.
        val peso = _estado.value.peso.toDoubleOrNull()
        val altura = _estado.value.altura.toDoubleOrNull()

        // Validação de domínio
        if (peso == null || altura == null || peso <= 0 || altura <= 0) {
            _estado.update {
                it.copy(
                    erro = "Digite valores válidos! Peso e altura devem ser maiores que zero."
                )
            }
            return
        }

        /// Chamada da regra de negócio
        val resultado = calcularIMCUseCase(peso, altura)

        // Atualiza estado com resultado
        _estado.update {
            it.copy(
                resultado = "%.2f".format(resultado.valor),
                classificacao = resultado.classificacao,
                erro = null
            )
        }
    }

    /**
     * Reseta o estado da tela para permitir novo cálculo.
     *
     * Decisão:
     * - Reset total evita inconsistências
     * - Facilita testes
     * - Fluxo claro para o usuário
     */
    private fun resetarEstado() {
        _estado.value = EstadoIMC()
    }
}