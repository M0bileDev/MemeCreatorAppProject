package com.example.memecreatorappproject.editor.presentation

import androidx.compose.ui.unit.IntSize

typealias TextBoxId = String

data class MemeEditorState(
    val templateSize: IntSize = IntSize.Zero,
    val abortWithoutSave: Boolean = false,
    val textBoxInteractionState: TextBoxInteractionState = TextBoxInteractionState.None,
    val memeTexts: List<MemeText> = emptyList(),
    val isEditMode: Boolean = true,
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

fun TextBoxInteractionState.hasEqualId(compareId: TextBoxId) =
    (this is TextBoxInteractionState.Selected && this.textBoxId == compareId) ||
        (this is TextBoxInteractionState.Editing && this.textBoxId == compareId)

fun TextBoxInteractionState.isMemeTextSelected(memeText: MemeText) = isFocused() && hasEqualId(memeText.id)

fun TextBoxInteractionState.isMemeTextEditing(memeText: MemeText) =
    this is TextBoxInteractionState.Editing &&
        this.textBoxId == memeText.id
