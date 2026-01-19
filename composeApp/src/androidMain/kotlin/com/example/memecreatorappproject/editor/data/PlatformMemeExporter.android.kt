package com.example.memecreatorappproject.editor.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.compose.ui.unit.IntSize
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.withTranslation
import com.example.memecreatorappproject.R
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.util.MemeRenderCalculator
import com.example.memecreatorappproject.editor.presentation.util.ScaledMemeText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val BYTES_TO_SKIP = 0

actual class PlatformMemeExporter(
    private val context: Context
) : MemeExporter {

    private val memeRenderCalculator = MemeRenderCalculator(
        displayDensity = context.resources.displayMetrics.density
    )

    actual override suspend fun exportMeme(
        backgroundImage: ByteArray,
        memeTexts: List<MemeText>,
        templateSize: IntSize,
        name: String,
        saveToStorageStrategy: SaveToStorageStrategy
    ) = withContext(Dispatchers.IO) {
        var bitmap: Bitmap? = null
        var outputBitmap: Bitmap? = null

        try {
            bitmap = BitmapFactory.decodeByteArray(
                backgroundImage,
                BYTES_TO_SKIP,
                backgroundImage.size
            )
            // TODO: further impl
        } catch (e: Exception) {

        } finally {
            // Release resources
            bitmap?.recycle()
            outputBitmap?.recycle()
        }
    }

    private suspend fun renderMeme(
        background: Bitmap,
        memeTexts: List<MemeText>,
        templateSize: IntSize
    ): Bitmap = withContext(Dispatchers.Default) {
        // Crete bitmap that can be mutable, because for nature bitmap
        // passed as an argument cannot
        // be changed (cant change pixel to different color),
        // mark isMutable = true, enable mutation of the bitmap
        val output = background.copy(
            //enable rgb color
            Bitmap.Config.ARGB_8888,
            true
        )
        //use bitmap as a background of the canvas
        val canvas = Canvas(output)
        val scaleFactors = memeRenderCalculator.calculateScaleFactors(
            bitmapWidth = background.width,
            bitmapHeight = background.height,
            templateSize = templateSize
        )
        val scaledMemeText = memeTexts.map { memeText ->
            memeRenderCalculator.calculateScaledMemeText(
                memeText = memeText,
                scaleFactors = scaleFactors,
                templateSize = templateSize
            )
        }
        scaledMemeText.forEach { scaledMemeText ->
            drawText(canvas, scaledMemeText)
        }
        output
    }

    private fun drawText(
        canvas: Canvas,
        scaledMemeText: ScaledMemeText
    ) {
        val impactTypeFace = ResourcesCompat.getFont(
            context,
            R.font.impact
        ) ?: Typeface.DEFAULT_BOLD

        val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = scaledMemeText.strokeWidth
            textSize = scaledMemeText.scaledFontSizePx
            typeface = impactTypeFace
            color = android.graphics.Color.BLACK
        }

        val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            textSize = scaledMemeText.scaledFontSizePx
            typeface = impactTypeFace
            color = android.graphics.Color.WHITE
        }

        //layout in which it's possible to give text some bounds and wrap lines if necessary
        val strokeLayout = StaticLayout.Builder.obtain(
            scaledMemeText.text,
            0,
            scaledMemeText.text.length,
            TextPaint(strokePaint),
            scaledMemeText.constraintWidth
        )
            .setAlignment(Layout.Alignment.ALIGN_CENTER)
            //no extra font padding
            .setIncludePad(false)
            .build()

        val fillLayout = StaticLayout.Builder.obtain(
            scaledMemeText.text,
            0,
            scaledMemeText.text.length,
            TextPaint(fillPaint),
            scaledMemeText.constraintWidth
        )
            .setAlignment(Layout.Alignment.ALIGN_CENTER)
            //no extra font padding
            .setIncludePad(false)
            .build()

        val textHeight = strokeLayout.height.toFloat()
        //iterate over each line of text and find the longest/wider one
        val textWidth =
            (0 until strokeLayout.lineCount).maxOfOrNull { strokeLayout.getLineWidth(it) } ?: 0f

        //actual padding
        val boxWidth = textWidth + scaledMemeText.textPaddingX * 2
        val boxHeight = textHeight + scaledMemeText.textPaddingY * 2

        //be default the pivot of the text is top left corner
        val centerX = scaledMemeText.scaledOffset.x + boxWidth / 2f
        val centerY = scaledMemeText.scaledOffset.y + boxHeight / 2f

        canvas.withTranslation(centerX, centerY) {
            scale(scaledMemeText.scale, scaledMemeText.scale)
            rotate(scaledMemeText.rotation)

            val textCenteringOffset = (scaledMemeText.constraintWidth - textWidth) / 2f
            translate(
                -boxWidth / 2f + scaledMemeText.textPaddingX - textCenteringOffset,
                -boxHeight / 2f + scaledMemeText.textPaddingY
            )

            strokeLayout.draw(this)
            fillLayout.draw(this)
        }
    }
}