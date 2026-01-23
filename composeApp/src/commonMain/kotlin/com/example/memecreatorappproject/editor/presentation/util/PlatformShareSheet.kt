package com.example.memecreatorappproject.editor.presentation.util

expect class PlatformShareSheet {
    suspend fun shareFile(filePath: String)
}