package com.example.memecreatorappproject.editor.presentation.util

class MemeRenderCalculator(
    private val displayDensity: Float
) {
    companion object {
        //consider a padding during drawing on canvas, 8dp between
        // text and border and 3dp as a text stroke
        private const val TEXT_PADDING_DP = 8f
        private const val STROKE_WIDTH_DP = 3f
    }

    fun calculateScaleFactors() {
        // TODO: implement
    }
}

data class ScaleFactors(
    val scaleX: Float,
    val scaleY: Float,
    val bitmapScale: Float
)