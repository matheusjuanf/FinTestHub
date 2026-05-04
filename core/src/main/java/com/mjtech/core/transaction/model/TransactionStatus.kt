package com.mjtech.core.transaction.model

sealed class TransactionStatus {
    object Idle : TransactionStatus()
    object Processing : TransactionStatus()
    data class Success(val transactionId: String, val message: String, val receipt: String) : TransactionStatus()
    data class Failure(val errorCode: String, val errorMessage: String) : TransactionStatus()
}