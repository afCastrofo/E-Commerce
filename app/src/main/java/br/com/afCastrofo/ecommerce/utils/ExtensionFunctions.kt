package br.com.afCastrofo.ecommerce.utils

import java.text.NumberFormat
import java.util.*

fun Double?.convertToCurrency(locale: Locale = Locale.forLanguageTag("pt-BR")): String {
    return NumberFormat.getCurrencyInstance(locale).format(this ?: 0.0)
}