package br.com.imcfacil.apresentacao.estado

/** Estado imutável da tela de IMC.
 *
 * A UI deve ser um reflexo direto desse estado.
 * Sempre que o estado muda, a UI reage.
 */
data class EstadoIMC(

    // Texto digitado pelo usuário no campo de peso e altura.
    // Mantive como String porque TextField trabalha com texto.
    val peso: String = "",
    val altura: String = "",

    // Resultado formatado do IMC para exibição.
    val resultado: String? = null,

    // Classificação do IMC (ex: Peso normal).
    val classificacao: String? = null,

    // Indica se existe erro de validação.
    val erro: String? = null,

    // Desabilitar botão quando inválido
    val podeCalcular: Boolean = peso.isNotBlank() && altura.isNotBlank()
) {
    // Estado derivado: indica se já existe um resultado
    val temResultado: Boolean
        get() = resultado != null
}