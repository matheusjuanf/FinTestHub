package com.mjtech.fintesthub.ui.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mjtech.core.common.Result
import com.mjtech.core.payment.InstallmentOption
import com.mjtech.core.payment.PaymentRepository
import com.mjtech.core.transaction.TransactionProcessor
import com.mjtech.core.transaction.enums.InstallmentType
import com.mjtech.core.transaction.enums.TransactionType
import com.mjtech.core.transaction.model.InstallmentDetails
import com.mjtech.core.transaction.model.Transaction
import com.mjtech.core.transaction.model.TransactionStatus
import com.mjtech.fintesthub.ui.checkout.models.TransactionResultUi
import com.mjtech.fintesthub.ui.common.mappers.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val paymentRepository: PaymentRepository,
    private val transactionProcessor: TransactionProcessor
) : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    init {
        loadPaymentMethods()
    }

    fun setTransactionAmount(amount: Long) {
        val total = amount / 100.0
        _uiState.update { it.copy(transactionAmount = total) }
    }

    fun onPaymentMethodSelected(methodId: String) {
        val selectedMethod = _uiState.value.availablePaymentMethods.find { it.id == methodId }
        val amount = _uiState.value.transactionAmount

        _uiState.update {
            it.copy(selectedPaymentMethodId = methodId)
        }

        val transactionType = try {
            TransactionType.valueOf(methodId)
        } catch (_: Exception) {
            Log.e(TAG, "Método de pagamento inválido: $methodId")
            return
        }


        val orderId = System.currentTimeMillis()
        _uiState.update {
            it.copy(
                transaction = Transaction(
                    id = orderId,
                    amount = amount,
                    type = transactionType,
                    installmentDetails = null
                )
            )
        }

        if (selectedMethod?.requiresInstallments == true && amount > 0.0) {
            loadInstallmentOptions(methodId, amount)
        } else if (selectedMethod?.requiresInstallments == false && amount > 0.0) {
            processPayment()
        }
    }

    fun onInstallmentSelected(installment: InstallmentOption) {
        _uiState.update {
            it.copy(
                transaction = it.transaction?.copy(
                    installmentDetails = InstallmentDetails(
                        installments = installment.count,
                        installmentType = InstallmentType.MERCHANT
                    )
                )
            )
        }
        processPayment()
    }

    fun processPayment() {
        val currentTransaction = _uiState.value.transaction

        if (currentTransaction == null || _uiState.value.transactionAmount <= 0) {
            handleError("Erro", "Dados da transação inválidos ou valor zerado.")
            return
        }

        viewModelScope.launch {
            transactionProcessor.processTransaction(currentTransaction).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Result.Success -> {
                        handleTransactionStatus(result.data)
                    }

                    is Result.Error -> {
                        handleError("Erro Técnico", result.error)
                    }
                }
            }
        }
    }

    fun resetTransactionResult() {
        _uiState.update { it.copy(transactionResult = null) }
    }

    private fun loadPaymentMethods() {
        viewModelScope.launch {
            paymentRepository.getAvailablePaymentMethods()
                .collect { result ->
                    _uiState.update { currentState ->
                        val mappedMethods = (result as? Result.Success)?.data?.map { it.toUi() }

                        currentState.copy(
                            paymentMethods = result,
                            availableUiMethods = mappedMethods ?: emptyList(),
                            isLoading = result is Result.Loading
                        )
                    }
                }
        }
    }

    private fun loadInstallmentOptions(methodId: String, amount: Double) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    installmentsOptions = Result.Loading,
                    isLoading = true
                )
            }

            paymentRepository.getInstallmentOptions(methodId, amount)
                .collect { result ->
                    _uiState.update { currentState ->
                        val isLoadingMethods = currentState.paymentMethods is Result.Loading
                        currentState.copy(
                            installmentsOptions = result,
                            isLoading = isLoadingMethods || (result is Result.Loading)
                        )
                    }
                }
        }
    }

    private fun handleTransactionStatus(status: TransactionStatus) {
        _uiState.update { it.copy(transactionStatus = status) }

        when (status) {
            is TransactionStatus.Processing -> {
                _uiState.update { it.copy(isLoading = true) }
            }

            is TransactionStatus.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        transactionResult = TransactionResultUi(
                            isSuccess = true,
                            title = "Sucesso!",
                            message = status.message
                        )
                    )
                }
            }

            is TransactionStatus.Failure -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        transactionResult = TransactionResultUi(
                            isSuccess = false,
                            title = "Transação Falhou",
                            message = "${status.errorCode}: ${status.errorMessage}"
                        )
                    )
                }
            }

            TransactionStatus.Idle -> {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    private fun handleError(title: String, message: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                transactionResult = TransactionResultUi(
                    isSuccess = false,
                    title = title,
                    message = message
                )
            )
        }
    }

    companion object {
        private val TAG = CheckoutViewModel::class.java.simpleName
    }
}