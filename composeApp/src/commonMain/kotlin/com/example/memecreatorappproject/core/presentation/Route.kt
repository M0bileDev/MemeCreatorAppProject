package com.example.memecreatorappproject.core.presentation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object MemeGallery : Route

    @Serializable
    data class MemeEditor(
        val templateId: TemplateId,
    ) : Route
}
