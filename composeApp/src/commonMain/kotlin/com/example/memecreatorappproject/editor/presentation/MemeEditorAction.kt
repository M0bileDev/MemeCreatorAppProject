package com.example.memecreatorappproject.editor.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import com.example.memecreatorappproject.core.presentation.MemeTemplate

sealed interface MemeEditorAction {
    data object OnGoBackClick : MemeEditorAction

    data object OnConfirmAbortWithoutSave : MemeEditorAction

    data object OnDismissAbortWithoutSave : MemeEditorAction

    data class OnSaveMemeConfirm(
        val memeTemplate: MemeTemplate,
    ) : MemeEditorAction

    data object OnTapOutsideSelectedText : MemeEditorAction

    data object OnAddTextClick : MemeEditorAction

    data class OnSelectMemeText(
        val textBoxId: TextBoxId,
    ) : MemeEditorAction

    data class OnEditMemeText(
        val textBoxId: TextBoxId,
    ) : MemeEditorAction

    data class OnMemeTextChange(
        val textBoxId: TextBoxId,
        val text: String,
    ) : MemeEditorAction

    data class OnDeleteMemeTextClick(
        val textBoxId: TextBoxId,
    ) : MemeEditorAction

    data class OnMemeTextTransformChange(
        val textBoxId: TextBoxId,
        val offset: Offset,
        val rotation: Float,
        val scale: Float,
    ) : MemeEditorAction

    data class OnContainerSizeChange(
        val size: IntSize,
    ) : MemeEditorAction
}
