package com.example.memecreatorappproject.editor.presentation.util

import androidx.compose.ui.unit.IntSize

class MemeRenderCalculator(
    private val displayDensity: Float,
) {
    companion object {
        // consider a padding during drawing on canvas, 8dp between
        // text and border and 3dp as a text stroke, Float as dp
        private const val TEXT_PADDING_DP = 8f
        private const val STROKE_WIDTH_DP = 3f
    }

    fun calculateScaleFactors(
        bitmapWidth: Int,
        bitmapHeight: Int,
        // meme template size
        templateSize: IntSize,
    ): ScaleFactors {
        val scaleX = if (templateSize.width > 0) bitmapWidth.toFloat() / templateSize.width else 1f
        val scaleY =
            if (templateSize.height > 0) bitmapHeight.toFloat() / templateSize.height else 1f

        val bitmapScale = (scaleX + scaleY) / 2

        return ScaleFactors(
            scaleX = scaleX,
            scaleY = scaleY,
            bitmapScale = bitmapScale,
        )
    }
}

data class ScaleFactors(
    val scaleX: Float,
    val scaleY: Float,
    val bitmapScale: Float,
)
