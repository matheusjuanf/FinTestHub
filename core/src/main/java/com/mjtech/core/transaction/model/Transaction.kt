package com.mjtech.core.transaction.model

import com.mjtech.core.transaction.enums.TransactionType
import kotlinx.serialization.Serializable

/**
 * @param id O ID do pagamento.
 * @param amount O valor do pagamento.
 * @param type O tipo de pagamento (crédito, débito, pix, etc.).
 * @param installmentDetails Detalhes do parcelamento, pode ser nulo.
 */
@Serializable
data class Transaction(
    val id: Long,
    val amount: Double,
    val type: TransactionType,
    val installmentDetails: InstallmentDetails?
)