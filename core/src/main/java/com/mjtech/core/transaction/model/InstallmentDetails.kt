package com.mjtech.core.transaction.model

import com.mjtech.core.transaction.enums.InstallmentType
import kotlinx.serialization.Serializable

/**
 * @param installments Quantidade de parcelas
 * @param installmentType Tipo de parcelamento
 */
@Serializable
data class InstallmentDetails(
    val installments: Int,
    val installmentType: InstallmentType
)