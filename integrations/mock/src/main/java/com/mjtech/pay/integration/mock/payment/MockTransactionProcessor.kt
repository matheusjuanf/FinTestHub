package com.mjtech.pay.integration.mock.payment

import com.mjtech.core.transaction.TransactionProcessor
import com.mjtech.core.transaction.model.Transaction
import com.mjtech.core.transaction.model.TransactionStatus
import kotlinx.coroutines.flow.Flow
import com.mjtech.core.common.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

internal class MockTransactionProcessor : TransactionProcessor {

    override fun processTransaction(
        transaction: Transaction,): Flow<Result<TransactionStatus>> = flow {
        emit(Result.Success(TransactionStatus.Processing))

        // Simula um atraso de processamento
        delay(2000)

        if (transaction.amount <= 1000.0) {
            emit(
                Result.Success(
                    TransactionStatus.Success(
                        transactionId = "MOCK-${System.currentTimeMillis()}",
                        message = "Pagamento Mock aprovado com sucesso!",
                        receipt = "Mock Receipt"
                    )
                )
            )
        } else {
            emit(
                Result.Success(
                    TransactionStatus.Failure(
                        errorCode = "LIMIT_EXCEEDED",
                        errorMessage = "O valor excede o limite permitido para o Mock (Max: 1000.0)."
                    )
                )
            )
        }
    }
}