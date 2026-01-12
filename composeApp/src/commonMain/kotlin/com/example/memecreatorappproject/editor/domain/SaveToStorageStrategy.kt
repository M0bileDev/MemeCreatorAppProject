package com.example.memecreatorappproject.editor.domain

/*
Multiple different strategies like save to external/internal storage, cache directory, tmp file etc...
 */
interface SaveToStorageStrategy {
    /**
     * There could be platform specific implementations
     */
    fun getFilePath(filename: String): String
}