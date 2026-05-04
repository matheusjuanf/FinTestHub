package com.mjtech.core.transaction

import com.mjtech.core.transaction.model.TransactionStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class TransactionResultBridge {
    private val _resultEvents = MutableSharedFlow<Pair<String, Result<TransactionStatus>>>()
    val resultEvents = _resultEvents.asSharedFlow()

    suspend fun postResult(transactionId: String, result: Result<TransactionStatus>) {
        _resultEvents.emit(transactionId to result)
    }
}