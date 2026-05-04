package com.mjtech.fintesthub.data.payment

import com.mjtech.core.common.Result
import com.mjtech.core.payment.InstallmentOption
import com.mjtech.core.payment.PaymentMethod
import com.mjtech.core.payment.PaymentRepository
import com.mjtech.core.transaction.enums.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockPaymentRepository : PaymentRepository {


    override fun getAvailablePaymentMethods(): Flow<Result<List<PaymentMethod>>> = flow {
        emit(Result.Loading)
        val mockMethods = listOf(
            PaymentMethod(
                id = TransactionType.DEBIT.name,
                name = TransactionType.DEBIT.text,
                maxInstallments = 1,
                requiresInstallments = false
            ),
            PaymentMethod(
                id = TransactionType.CREDIT.name,
                name = TransactionType.CREDIT.text,
                maxInstallments = 12,
                requiresInstallments = true
            ),
            PaymentMethod(
                id = TransactionType.PIX.name,
                name = TransactionType.PIX.text,
                maxInstallments = 1,
                requiresInstallments = false
            ),
            PaymentMethod(
                id = TransactionType.VOUCHER.name,
                name = TransactionType.VOUCHER.text,
                maxInstallments = 1,
                requiresInstallments = false
            )
        )
        emit(Result.Success(mockMethods))
    }

    override fun getInstallmentOptions(
        methodId: String,
        totalAmount: Double
    ): Flow<Result<List<InstallmentOption>>> = flow {
        emit(Result.Loading)
        val options = when (methodId) {
            TransactionType.CREDIT.name -> listOf(
                createInstallment(1, totalAmount, 0.0),
                createInstallment(2, totalAmount, 0.0),
                createInstallment(3, totalAmount, 0.0),
                createInstallment(4, totalAmount, 0.0)
            )

            else -> listOf(createInstallment(1, totalAmount, 0.0))
        }
        emit(Result.Success(options))
    }

    internal fun createInstallment(count: Int, amount: Double, rate: Double): InstallmentOption {
        val totalWithInterest = amount * (1.0 + rate)
        val monthly = totalWithInterest / count
        return InstallmentOption(
            count = count,
            totalAmount = totalWithInterest,
            interestRate = rate,
            monthlyAmount = monthly
        )
    }
}