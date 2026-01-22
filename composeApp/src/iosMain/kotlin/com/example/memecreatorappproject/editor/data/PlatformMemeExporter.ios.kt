@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.example.memecreatorappproject.editor.data

import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.util.MemeRenderCalculator
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIScreen

actual class PlatformMemeExporter : MemeExporter {

    private val memeRenderCalculator = MemeRenderCalculator(
        displayDensity = UIScreen.mainScreen.scale.toFloat()
    )

    actual override suspend fun exportMeme(
        backgroundImage: ByteArray,
        memeTexts: List<MemeText>,
        templateSize: IntSize,
        name: String,
        saveToStorageStrategy: SaveToStorageStrategy
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val backgroundImage = createBackgroundImage(
                imageBytes = backgroundImage
            )
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
            NSData.create(
                //start from first byte
                bytes = pinned.addressOf(0),
                length = imageBytes.size.toULong()
            )
        }
        return UIImage.imageWithData(imageData)
    }

    private fun renderMeme(
        backgroundImage: UIImage,
        memeTexts: List<MemeText>,
        templateSize: IntSize
    ): UIImage? {
        val imageSize = IntSize(
            // useContents -> temporary copy of the size
            width = backgroundImage.size.useContents { width.toInt() },
            height = backgroundImage.size.useContents { height.toInt() }
        )

        //move canvas in certain state and draw on the canvas
        //1. start drawing on the ios canvas -> start the draw context (image surface)
        UIGraphicsBeginImageContextWithOptions(
            CGSizeMake(imageSize.width.toDouble(), imageSize.height.toDouble()),
            false,
            0.0
        )

        //canvas context
        val context = UIGraphicsGetCurrentContext()
        if (context == null) {
            //rendering image is done
            UIGraphicsEndImageContext()
            return null
        }

        //2. drawing background (draw on the image surface)
        backgroundImage.drawInRect(
            CGRectMake(
                // x,y  -> starting point, top left corner
                x = 0.0,
                y = 0.0,
                width = imageSize.width.toDouble(),
                height = imageSize.height.toDouble()
            )
        )

        //provide scale factors used to scale meme texts
        val scaleFactors = memeRenderCalculator.calculateScaleFactors(
            bitmapWidth = imageSize.width,
            bitmapHeight = imageSize.height,
            templateSize = templateSize
        )
        val scaledMemeTexts = memeTexts.map { memeText ->
            memeRenderCalculator.calculateScaledMemeText(
                memeText = memeText,
                scaleFactors = scaleFactors,
                templateSize = templateSize
            )
        }

        //3. draw texts on the canvas
        scaledMemeTexts.forEach { scaledMemeText ->
            // TODO: provide draw text function
        }

        //4. get image after drawing
        val resultImage = UIGraphicsGetImageFromCurrentImageContext()

        //done with drawings
        UIGraphicsEndImageContext()

        return resultImage
    }
}