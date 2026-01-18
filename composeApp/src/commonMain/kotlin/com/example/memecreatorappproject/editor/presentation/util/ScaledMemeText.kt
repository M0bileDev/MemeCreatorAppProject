package com.example.memecreatorappproject.editor.presentation.util

import androidx.compose.ui.geometry.Offset
import com.example.memecreatorappproject.editor.presentation.MemeText

data class ScaledMemeText(
    val text: String,
    // scaling up operations made on canvas
    val scaledOffset: Offset,
    // scaled canvas could change, and also text has to be scaled
    val scaledFontSizePx: Float,
    val strokeWidth: Float,
    val constraintWidth: Int,
    val textPaddingX: Float,
    val textPaddingY: Float,
    val rotation: Float,
    val scale: Float,
    val originalText: MemeText,
)
