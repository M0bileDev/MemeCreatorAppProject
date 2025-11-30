package com.example.memecreatorappproject.core.presentation

import memecreatorappproject.composeapp.generated.resources.Res
import memecreatorappproject.composeapp.generated.resources.allDrawableResources
import org.jetbrains.compose.resources.DrawableResource

typealias TemplateId = String

data class MemeTemplate(
    val id: TemplateId,
    val drawable: DrawableResource,
)

val memeTemplates =
    Res.allDrawableResources
        .filter { it.key.startsWith("meme_template") }
        .map { (key, value) -> MemeTemplate(key, value) }
