package com.mjtech.core.payment

import com.mjtech.core.common.Result
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {

    fun getAvailablePaymentMethods(): Flow<Result<List<PaymentMethod>>>

    fun getInstallmentOptions(
        methodId: String,
        totalAmount: Double
    ): Flow<Result<List<InstallmentOption>>>
}