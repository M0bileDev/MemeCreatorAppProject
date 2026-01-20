package com.example.memecreatorappproject.editor.data

import android.content.Context
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import java.io.File

actual class CacheStorageStrategy(
    private val context: Context
) :
    SaveToStorageStrategy {
    actual override fun getFilePath(filename: String): String {
        return File(context.cacheDir, filename).absolutePath
    }
}