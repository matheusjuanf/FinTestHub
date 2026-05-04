package com.mjtech.core.print

import com.mjtech.core.common.Result
import kotlinx.coroutines.flow.Flow

interface PrintRepository {

    fun printSimpleText(textPrint: TextPrint): Flow<Result<Unit>>

    fun printText(linesText: List<TextPrint>): Flow<Result<Unit>>

    fun printQrCode(text: String): Flow<Result<Unit>>

    fun printBitmap(imageData: ImageData): Flow<Result<Unit>>
}