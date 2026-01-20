package com.example.memecreatorappproject.editor.data

import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy

expect class CacheStorageStrategy : SaveToStorageStrategy {
    override fun getFilePath(filename: String): String
}