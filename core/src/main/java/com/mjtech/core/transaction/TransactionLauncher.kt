package com.mjtech.core.transaction

import com.mjtech.core.transaction.model.Transaction

interface TransactionLauncher {
    fun launch(transaction: Transaction)
}