package br.com.imcfacil.ui.fluxo

import br.com.imcfacil.dominio.modelo.ResultadoIMC

/**
 * Estado único que representa todo o fluxo do IMC.
 *
 * A UI NÃO guarda estado próprio relevante.
 * Ela apenas reage a este objeto.
 */
data class EstadoUiImc(
    val etapaAtual: Int = 0,
    val pesoDigitado: String = "",
    val alturaDigitada: String = "",
    val resultadoImc: ResultadoIMC? = null
)