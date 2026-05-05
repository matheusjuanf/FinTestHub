package com.mjtech.fintesthub.ui.common.mappers

import androidx.compose.ui.graphics.Color
import com.mjtech.core.payment.PaymentMethod
import com.mjtech.fintesthub.R
import com.mjtech.fintesthub.ui.checkout.models.PaymentMethodUi
import com.mjtech.fintesthub.ui.theme.MainColor
import com.mjtech.fintesthub.ui.theme.MainDarkColor
import com.mjtech.fintesthub.ui.theme.MainLightColor
import com.mjtech.fintesthub.ui.theme.SecondaryColor
import com.mjtech.fintesthub.ui.theme.SecondaryDarkColor

fun PaymentMethod.toUi(): PaymentMethodUi {
    val (iconRes, color, description) = getUiAssetsForPaymentType(this.id)

    return PaymentMethodUi(
        id = this.id,
        name = this.name,
        description = description,
        icon = iconRes,
        color = color
    )
}

private fun getUiAssetsForPaymentType(methodId: String): Triple<Int, Color, String> {
    return when (methodId) {
        "DEBIT" -> Triple(R.drawable.ic_credit_card, MainColor, "Cartão de Débito")
        "CREDIT" -> Triple(R.drawable.ic_credit_card, MainLightColor, "Cartão de Crédito")
        "PIX" -> Triple(R.drawable.ic_qrcode, SecondaryDarkColor, "PIX e Carteiras Digitais")
        "VOUCHER" -> Triple(R.drawable.ic_credit_card, SecondaryColor, "Vale alimentação ou refeição")
        else -> Triple(R.drawable.ic_credit_card, Color.Gray, "Método desconhecido")
    }
}
