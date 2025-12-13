package com.example.memecreatorappproject.editor.presentation

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MemeEditorViewModel : ViewModel() {
    private val _state: MutableStateFlow<MemeEditorState> = MutableStateFlow(MemeEditorState())
    val state get() = _state.asStateFlow()

    fun onAction(action: MemeEditorAction) {
        when (action) {
            MemeEditorAction.OnAddTextClick -> {
                TODO()
            }

            MemeEditorAction.OnConfirmAbortWithoutSave -> {
                TODO()
            }

            is MemeEditorAction.OnContainerSizeChange -> {
                TODO()
            }

            is MemeEditorAction.OnDeleteMemeTextClick -> {
                deleteMemeText(action.textBoxId)
            }

            MemeEditorAction.OnDismissAbortWithoutSave -> {
                TODO()
            }

            is MemeEditorAction.OnEditMemeText -> {
                editMemeText(action.textBoxId)
            }

            MemeEditorAction.OnGoBackClick -> {
                TODO()
            }

            is MemeEditorAction.OnMemeTextChange -> {
                updateMemeText(action.textBoxId, action.text)
            }

            is MemeEditorAction.OnMemeTextTransformChange -> {
                textTransformChange(action.textBoxId, action.offset, action.scale, action.rotation)
            }

            is MemeEditorAction.OnSaveMemeConfirm -> {
                TODO()
            }

            is MemeEditorAction.OnSelectMemeText -> {
                selectMemeText(action.textBoxId)
            }

            MemeEditorAction.OnTapOutsideSelectedText -> {
                TODO()
            }
        }
    }

    private fun textTransformChange(
        textBoxId: TextBoxId,
        offset: Offset,
        scale: Float,
        rotation: Float,
    ) {
        // TODO: not implemented yet
    }

    private fun deleteMemeText(textBoxId: TextBoxId) {
        _state.update {
            it.copy(
                memeTexts =
                    it.memeTexts.filter { memeText ->
                        memeText.id != textBoxId
                    },
            )
        }
    }

    private fun selectMemeText(textBoxId: TextBoxId) {
        _state.update {
            it.copy(
                textBoxInteractionState = TextBoxInteractionState.Selected(textBoxId),
            )
        }
    }

    private fun updateMemeText(
        textBoxId: TextBoxId,
        text: String,
    ) {
        _state.update {
            it.copy(
                memeTexts =
                    it.memeTexts.map { memeText ->
                        if (memeText.id == textBoxId) {
                            memeText.copy(text = text)
                        } else {
                            memeText
                        }
                    },
            )
        }
    }

    private fun editMemeText(textBoxId: TextBoxId) {
        _state.update {
            it.copy(
                textBoxInteractionState = TextBoxInteractionState.Editing(textBoxId),
            )
        }
    }
}
