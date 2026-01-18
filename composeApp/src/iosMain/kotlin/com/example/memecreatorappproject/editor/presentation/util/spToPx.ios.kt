package com.example.memecreatorappproject.editor.presentation.util

import androidx.compose.ui.unit.TextUnit
import platform.UIKit.UIScreen

actual fun TextUnit.toPx(): Float = this.value * UIScreen.mainScreen.scale.toFloat()
