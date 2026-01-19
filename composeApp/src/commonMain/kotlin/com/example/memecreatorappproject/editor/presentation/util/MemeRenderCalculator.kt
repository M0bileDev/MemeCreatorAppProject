package com.example.memecreatorappproject.editor.presentation.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.presentation.MemeText
import kotlin.math.roundToInt

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

    fun calculateScaledMemeText(
        memeText: MemeText,
        scaleFactors: ScaleFactors,
        templateSize: IntSize,
    ): ScaledMemeText {
        val (scaleX, scaleY, bitmapScale) = scaleFactors

        // absolute offset for the text on the final resulting bitmap
        val scaledOffset =
            Offset(
                x = (memeText.offsetRatioX * templateSize.width) * scaleX,
                y = (memeText.offsetRatioY * templateSize.height) * scaleY,
            )

        // text padding of the content padding

        // dp to px
        val textPaddingPx = TEXT_PADDING_DP * displayDensity
        val textPaddingBitmapX = textPaddingPx * scaleX
        val textPaddingBitmapY = textPaddingPx * scaleY

        val scaledFontSize = memeText.fontSize * bitmapScale

        val strokeWidthPx = STROKE_WIDTH_DP * displayDensity * scaleX

        // padding is applied for both ends
        val paddingDp = TEXT_PADDING_DP * 2
        val paddingPx = paddingDp * displayDensity

        // dimension in which the text is allowed to expend with
        val constraintWidth =
            ((templateSize.width / memeText.scale) * scaleX - paddingPx * scaleX)
                .roundToInt()
                .coerceAtLeast(1)

        return ScaledMemeText(
            text = memeText.text,
            scaledOffset = scaledOffset,
            scaledFontSizePx = scaledFontSize.toPx(),
            strokeWidth = strokeWidthPx,
            constraintWidth = constraintWidth,
            textPaddingX = textPaddingBitmapX,
            textPaddingY = textPaddingBitmapY,
            rotation = memeText.rotation,
            scale = memeText.scale,
            originalText = memeText,
        )
    }
}

data class ScaleFactors(
    val scaleX: Float,
    val scaleY: Float,
    val bitmapScale: Float,
)
