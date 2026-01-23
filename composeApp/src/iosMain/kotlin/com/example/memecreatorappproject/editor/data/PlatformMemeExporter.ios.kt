@file:OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)

package com.example.memecreatorappproject.editor.data

import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.util.MemeRenderCalculator
import com.example.memecreatorappproject.editor.presentation.util.ScaledMemeText
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGContextRef
import platform.CoreGraphics.CGContextRestoreGState
import platform.CoreGraphics.CGContextRotateCTM
import platform.CoreGraphics.CGContextSaveGState
import platform.CoreGraphics.CGContextScaleCTM
import platform.CoreGraphics.CGContextTranslateCTM
import platform.CoreGraphics.CGFloat
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSizeMake
import platform.Foundation.NSData
import platform.Foundation.NSNumber
import platform.Foundation.NSString
import platform.Foundation.create
import platform.Foundation.writeToFile
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.NSLineBreakByWordWrapping
import platform.UIKit.NSMutableParagraphStyle
import platform.UIKit.NSParagraphStyleAttributeName
import platform.UIKit.NSStrokeColorAttributeName
import platform.UIKit.NSStrokeWidthAttributeName
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIScreen
import platform.UIKit.boundingRectWithSize
import platform.UIKit.drawWithRect
import kotlin.math.PI

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
            ) ?: throw Exception("Failed to create background image")

            val outputImage = renderMeme(
                backgroundImage = backgroundImage,
                memeTexts = memeTexts,
                templateSize = templateSize
            ) ?: throw Exception("Failed to create output image")

            Result.success("")
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            Result.failure(e)
        }
    }

    private fun saveMemeToFile(
        image: UIImage,
        fileName: String,
        saveToStorageStrategy: SaveToStorageStrategy
    ): Result<String> {
        val jpegData = UIImageJPEGRepresentation(image, 90.0)
            ?: return Result.failure(Exception("Failed to create image"))
        val filePath = saveToStorageStrategy.getFilePath(fileName)
        val saved = jpegData.writeToFile(filePath, atomically = true)

        return if (saved) {
            Result.success(filePath)
        } else {
            Result.failure(Exception("Failed to save file"))
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
            drawText(
                context = context,
                scaledMemeText = scaledMemeText
            )
        }

        //4. get image after drawing
        val resultImage = UIGraphicsGetImageFromCurrentImageContext()

        //done with drawings
        UIGraphicsEndImageContext()

        return resultImage
    }

    private fun drawText(context: CGContextRef, scaledMemeText: ScaledMemeText) {
        val textNS = NSString.create(scaledMemeText.text)
        val attributes = createMemeTextAttributes(
            fontSize = scaledMemeText.scaledFontSizePx,
            strokeWidth = scaledMemeText.scaledFontSizePx
        )
        //rectangle that surrounds the text, in height text is not limited in any way
        val boundingRect = textNS?.boundingRectWithSize(
            size = CGSizeMake(scaledMemeText.constraintWidth.toDouble(), CGFloat.MAX_VALUE),
            options = 1L shl 0,
            attributes = attributes,
            context = null
        ) ?: return

        val textHeight = boundingRect.useContents { size.height.toFloat() }
        val textWidth = boundingRect.useContents { size.width.toFloat() }

        val boxWidth = textWidth + scaledMemeText.textPaddingX * 2
        val boxHeight = textHeight + scaledMemeText.textPaddingY * 2

        val centerX = scaledMemeText.scaledOffset.x + boxWidth / 2
        val centerY = scaledMemeText.scaledOffset.y + boxHeight / 2

        //initiate transform operation, context -> canvas context
        CGContextSaveGState(context)

        //scaled around the pivot of the text
        CGContextTranslateCTM(context, centerX.toDouble(), centerY.toDouble())
        //scaled
        CGContextScaleCTM(context, scaledMemeText.scale.toDouble(), scaledMemeText.scale.toDouble())
        CGContextRotateCTM(context, scaledMemeText.rotation * PI / 180.0)

        val textCenteringOffset = (scaledMemeText.constraintWidth - textWidth) / 2f
        CGContextTranslateCTM(
            context,
            (-boxWidth / 2f + scaledMemeText.textPaddingX - textCenteringOffset).toDouble(),
            (-boxHeight / 2f + scaledMemeText.textPaddingY).toDouble(),
        )

        textNS.drawWithRect(
            rect = CGRectMake(
                0.0,
                0.0,
                scaledMemeText.constraintWidth.toDouble(),
                textHeight.toDouble()
            ),
            // 1L shl 0 -> 1L
            options = 1L shl 0,
            attributes = attributes,
            null
        )

        //restore the original state of the canvas
        CGContextRestoreGState(context)
    }

    private fun createMemeTextAttributes(
        fontSize: Float,
        strokeWidth: Float
    ): Map<Any?, Any?> {
        // "Imapct" font is supported by default by ios
        val font =
            UIFont.fontWithName("Impact", fontSize.toDouble())
                ?: UIFont.boldSystemFontOfSize(
                    fontSize.toDouble()
                )

        val paragraphStyle = NSMutableParagraphStyle().apply {
            setAlignment(NSTextAlignmentCenter)
            setLineBreakMode(NSLineBreakByWordWrapping)
        }

        return mapOf(
            NSFontAttributeName to font,
            NSForegroundColorAttributeName to UIColor.whiteColor,
            NSStrokeColorAttributeName to UIColor.blackColor,
            NSStrokeWidthAttributeName to NSNumber(strokeWidth),
            NSParagraphStyleAttributeName to paragraphStyle
        )
    }
}