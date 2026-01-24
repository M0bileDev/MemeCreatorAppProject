package com.example.memecreatorappproject.editor.data

import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual class CacheStorageStrategy :
    SaveToStorageStrategy {
    actual override fun getFilePath(filename: String): String {
        val cacheDirectory = NSSearchPathForDirectoriesInDomains(
            NSCachesDirectory,
            NSUserDomainMask,
            true
        ).firstOrNull() as? String ?: throw IllegalStateException("Could not find cache directory")

        return "$cacheDirectory/$filename"
    }
}