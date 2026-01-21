package com.example.memecreatorappproject.editor.data

import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy

actual class CacheStorageStrategy :
    SaveToStorageStrategy {
    actual override fun getFilePath(filename: String): String {
        TODO("Not yet implemented")
    }
}