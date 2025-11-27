package com.example.memecreatorappproject.editor.presentation

typealias TextId = String

data class MemeText(
    val id: TextId,
    val text: String,
    val fontSize: Float = 36f,
    val offsetRatioX: Float = 0f,
    val offsetRatioY: Float = 0f,
    val rotation: Float = 0f,
    val scale: Float = 2f,
)
