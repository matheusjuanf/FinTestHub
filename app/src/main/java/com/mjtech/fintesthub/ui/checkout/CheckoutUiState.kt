package com.mjtech.fintesthub.ui.checkout

import com.mjtech.core.common.Result
import com.mjtech.core.payment.InstallmentOption
import com.mjtech.core.transaction.model.Transaction
import com.mjtech.core.payment.PaymentMethod
import com.mjtech.core.transaction.model.TransactionStatus
import com.mjtech.fintesthub.ui.checkout.models.PaymentMethodUi
import com.mjtech.fintesthub.ui.checkout.models.TransactionResultUi

data class CheckoutUiState(
    val transactionAmount: Double = 0.0,
    val selectedPaymentMethodId: String? = null,
    val paymentMethods: Result<List<PaymentMethod>>? = null,
    val availableUiMethods: List<PaymentMethodUi> = emptyList(),
    val installmentsOptions: Result<List<InstallmentOption>>? = null,
    val isLoading: Boolean = false,
    val transactionStatus: TransactionStatus = TransactionStatus.Idle,
    val transaction: Transaction? = null,
    val transactionResult: TransactionResultUi? = null
) {
    val availablePaymentMethods: List<PaymentMethod>
        get() = (paymentMethods as? Result.Success)?.data ?: emptyList()
}