package com.mjtech.fintesthub.di

import com.mjtech.core.payment.PaymentRepository
import com.mjtech.core.settings.SettingsRepository
import com.mjtech.fintesthub.data.payment.MockPaymentRepository
import com.mjtech.fintesthub.data.settings.MockSettingsRepository
import com.mjtech.fintesthub.ui.checkout.CheckoutViewModel
import com.mjtech.fintesthub.ui.settings.SettingsViewModel
import com.mjtech.fintesthub.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<PaymentRepository> { MockPaymentRepository() }

    single<SettingsRepository> { MockSettingsRepository(get()) }

    viewModel { SplashViewModel(get()) }

    viewModel { CheckoutViewModel(get(), get()) }

    viewModel { SettingsViewModel(get()) }

}