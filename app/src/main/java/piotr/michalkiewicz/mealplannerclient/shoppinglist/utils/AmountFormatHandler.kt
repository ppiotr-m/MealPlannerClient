package piotr.michalkiewicz.mealplannerclient.shoppinglist.utils

import java.math.BigDecimal

class AmountFormatHandler {
    fun normalizeAmount(amount: String): Double {
        return try {
            amount.toDouble()
        } catch (e: NumberFormatException) {
            return if (amount.contains('/', false)) {
                parseAmountWithSlash(amount)
            } else if (amount.contains('-', false)) {
                parseAmountWithDash(amount)
            } else {
                return -1.0
            }
        }
    }

    private fun parseAmountWithSlash(amount: String): Double {
        val slashIndex = amount.indexOf('/', 0, false)
        if (slashIndex > 0) {
            val firstPartOfNumber = amount[0].toString().toDouble()
            val secondPartOfNumber = amount.substring(slashIndex + 1).toDouble()
            if ((firstPartOfNumber == 0.0) || (secondPartOfNumber == 0.0)) {
                return -1.0
            }
            val result = firstPartOfNumber.toBigDecimal()
                .divide(secondPartOfNumber.toBigDecimal(), 2, BigDecimal.ROUND_HALF_UP)
            return result.toDouble()
        }
        return -1.0
    }

    private fun parseAmountWithDash(amount: String): Double {
        val slashIndex = amount.indexOf('-', 0, false)
        if (slashIndex <= 0) return -1.0
        if (slashIndex == 1) return amount[0].toString().toDouble()
        return amount.substring(0, slashIndex - 1).toDouble()
    }
}