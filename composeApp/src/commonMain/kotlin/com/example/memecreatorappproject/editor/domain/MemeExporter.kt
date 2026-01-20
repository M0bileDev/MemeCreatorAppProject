package com.example.memecreatorappproject.editor.domain

import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.presentation.MemeText
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface MemeExporter {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun exportMeme(
        backgroundImage: ByteArray,
        memeTexts: List<MemeText>,
        templateSize: IntSize,
        name: String = "meme_${Uuid.random()}.jpg",
        saveToStorageStrategy: SaveToStorageStrategy
    ) : Result<String>
}