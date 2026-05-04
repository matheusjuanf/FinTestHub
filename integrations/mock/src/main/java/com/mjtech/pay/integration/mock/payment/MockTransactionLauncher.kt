package com.mjtech.pay.integration.mock.payment

import android.util.Log
import com.mjtech.core.transaction.TransactionLauncher
import com.mjtech.core.transaction.model.Transaction

internal class MockTransactionLauncher: TransactionLauncher {

    companion object {
        val TAG = MockTransactionLauncher::class.java.simpleName
    }
    override fun launch(transaction: Transaction) {
        Log.d(TAG, "launch: $transaction")
    }
}