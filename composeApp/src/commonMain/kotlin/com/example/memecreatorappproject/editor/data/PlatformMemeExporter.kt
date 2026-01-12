@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.example.memecreatorappproject.editor.data

import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import com.example.memecreatorappproject.editor.presentation.MemeText

expect class PlatformMemeExporter : MemeExporter {
    override suspend fun exportMeme(
        backgroundImage: ByteArray,
        memeTexts: List<MemeText>,
        templateSize: IntSize,
        name: String,
        saveToStorageStrategy: SaveToStorageStrategy
    )
}