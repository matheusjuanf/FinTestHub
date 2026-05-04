package com.mjtech.core.transaction

import com.mjtech.core.transaction.model.Transaction
import com.mjtech.core.transaction.model.TransactionStatus
import kotlinx.coroutines.flow.Flow
import com.mjtech.core.common.Result

interface TransactionProcessor {

    fun processTransaction(transaction: Transaction): Flow<Result<TransactionStatus>>
}