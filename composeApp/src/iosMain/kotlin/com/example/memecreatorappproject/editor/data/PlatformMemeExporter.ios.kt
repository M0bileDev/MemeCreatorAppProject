@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.example.memecreatorappproject.editor.data

import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import com.example.memecreatorappproject.editor.presentation.MemeText
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIImage

actual class PlatformMemeExporter : MemeExporter {
    actual override suspend fun exportMeme(
        backgroundImage: ByteArray,
        memeTexts: List<MemeText>,
        templateSize: IntSize,
        name: String,
        saveToStorageStrategy: SaveToStorageStrategy
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            // TODO: further implementation
            Result.success("")
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Result.failure(e)
        }


    }

    private fun createBackgroundImage(imageBytes: ByteArray): UIImage? {
        // extension function for kotlin bytes arrays in ios, ensures image bytes
        // stay accessible during execution block
        val imageData = imageBytes.usePinned { pinned ->
            NSData.Companion.create(
                //start from first byte
                bytes = pinned.addressOf(0),
                length = imageBytes.size.toULong()
            )
        }
        return UIImage.imageWithData(imageData)
    }
}