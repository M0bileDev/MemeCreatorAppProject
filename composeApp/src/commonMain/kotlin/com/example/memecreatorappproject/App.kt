package com.example.memecreatorappproject

import androidx.compose.runtime.Composable
import com.example.memecreatorappproject.core.theme.MemeCreatorTheme
import com.example.memecreatorappproject.gallery.presentation.MemeGalleryScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MemeCreatorTheme {
        MemeGalleryScreen { memeTemplate -> Unit }
    }
}
