package com.example.memecreatorappproject.editor.presentation

import androidx.compose.ui.unit.IntSize

typealias TextBoxId = String

data class MemeEditorState(
    val templateSize: IntSize = IntSize.Zero,
    val abortWithoutSave: Boolean = false,
    val textBoxInteractionState: TextBoxInteractionState = TextBoxInteractionState.None,
    // test purpose only!
    val memeTexts: List<MemeText> =
        listOf(
            MemeText(
                id = "0",
                text = "tap to edit".uppercase(),
            ),
        ),
)

sealed interface TextBoxInteractionState {
    data object None : TextBoxInteractionState

    data class Selected(
        val textBoxId: TextBoxId,
    ) : TextBoxInteractionState

    data class Editing(
        val textBoxId: TextBoxId,
    ) : TextBoxInteractionState
}

fun TextBoxInteractionState.isFocused() = this != TextBoxInteractionState.None
