package com.mjtech.pay.integration.mock.di

import com.mjtech.core.transaction.TransactionLauncher
import com.mjtech.core.transaction.TransactionProcessor
import com.mjtech.pay.integration.mock.payment.MockTransactionLauncher
import com.mjtech.pay.integration.mock.payment.MockTransactionProcessor
import org.koin.dsl.module

val mockModule = module {
    factory<TransactionLauncher> { MockTransactionLauncher() }
    factory<TransactionProcessor> { MockTransactionProcessor() }
}