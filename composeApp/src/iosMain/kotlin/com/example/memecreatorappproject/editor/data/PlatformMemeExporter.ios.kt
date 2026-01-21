package com.example.memecreatorappproject.editor.data

import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import com.example.memecreatorappproject.editor.presentation.MemeText

actual class PlatformMemeExporter : MemeExporter {
    actual override suspend fun exportMeme(
        backgroundImage: ByteArray,
        memeTexts: List<MemeText>,
        templateSize: IntSize,
        name: String,
        saveToStorageStrategy: SaveToStorageStrategy
    ): Result<String> {
        TODO("Not yet implemented")
    }
}