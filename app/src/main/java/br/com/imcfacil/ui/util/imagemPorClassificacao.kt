package br.com.imcfacil.ui.util

import br.com.imcfacil.R

fun imagemPorClassificacao(classificacao: String): Int {
    return when (classificacao) {
        "Abaixo do peso" -> R.drawable.iv_underweight
        "Peso normal" -> R.drawable.iv_normal
        "Sobrepeso" -> R.drawable.iv_overweight
        else -> R.drawable.iv_obese
    }
}