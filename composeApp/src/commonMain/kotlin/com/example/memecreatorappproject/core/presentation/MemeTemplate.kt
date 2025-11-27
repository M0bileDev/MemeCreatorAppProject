package com.example.memecreatorappproject.core.presentation

import memecreatorappproject.composeapp.generated.resources.Res
import memecreatorappproject.composeapp.generated.resources.allDrawableResources
import org.jetbrains.compose.resources.DrawableResource

data class MemeTemplate(
    val id: String,
    val drawable: DrawableResource,
)

val memeTemplates =
    Res.allDrawableResources
        .filter { it.key.startsWith("meme_template") }
        .map { (key, value) -> MemeTemplate(key, value) }
