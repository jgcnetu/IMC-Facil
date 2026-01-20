package br.com.imcfacil.dominio.caso_de_uso

import br.com.imcfacil.dominio.modelo.ResultadoIMC

/** Caso de uso responsável por calcular o IMC.
 *
 * Um UseCase representa UMA ação clara do sistema,
 * normalmente iniciada pelo usuário.
 */

class CalcularIMCUseCase {

    /**
     * Usei operator fun invoke para permitir que a classe
     * seja chamada como uma função.
     *
     * Exemplo:
     * calcularIMCUseCase(peso, altura)
     *
     * Isso deixa o código mais expressivo e limpo.
     */
    operator fun invoke(peso: Double, altura: Double): ResultadoIMC {

        // Regra matemática pura do IMC.
        // Não há validação aqui ainda, pois isso pode evoluir conforme regras do negócio.
        val imc = peso / (altura * altura)

        // Classificação baseada em regras bem definidas.
        // Preferi usar when ao if encadeado por ser mais legível.
        val classificacao = when {

            imc < 18.5 -> "Abaixo do peso"
            imc < 24.9 -> "Peso normal"
            imc < 29.9 -> "Sobrepeso"
            else -> "Obesidade"
        }

        // Retornei um modelo de domínio, mantendo a regra desacoplada da UI.
        return ResultadoIMC(imc, classificacao)
    }
}