package br.com.imcfacil.dominio.modelo

/** Modelo de domínio que representa o resultado do cálculo de IMC.
 *
 * Importante:
 * - NÃO conhece UI
 * - NÃO conhece Android
 * - Existe apenas para representar o estado do negócio
 */

data class ResultadoIMC(

    // Valor numérico do IMC calculado.
    // Usei Double por ser mais preciso para cálculos matemáticos.
    val valor: Double,

    // Classificação textual baseada no valor do IMC.
    // String simples pois pertence à regra de negócio, não à apresentação (formatação visual).
    val classificacao: String
)