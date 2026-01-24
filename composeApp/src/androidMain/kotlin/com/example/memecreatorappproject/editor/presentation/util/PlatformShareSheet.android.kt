package com.example.memecreatorappproject.editor.presentation.util

import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.content.Intent.EXTRA_STREAM
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import androidx.core.content.FileProvider
import java.io.File

private const val FILE_PROVIDER = "fileprovider"
private const val IMAGE_JPEG = "image/jpeg"

actual class PlatformShareSheet(
    private val context: Context
) {
    actual fun shareFile(filePath: String) {
        val file = File(filePath)
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.$FILE_PROVIDER",
            file
        )

        val intent = Intent(ACTION_SEND).apply {
            type = IMAGE_JPEG
            putExtra(EXTRA_STREAM, uri)
            addFlags(FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }

        val chooser = Intent.createChooser(intent, null).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(chooser)
    }
}