@file:OptIn(ExperimentalUuidApi::class)

package com.example.memecreatorappproject.editor.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val CONTAINER_PADDING = 0.25f

class MemeEditorViewModel : ViewModel() {
    private val _state: MutableStateFlow<MemeEditorState> = MutableStateFlow(MemeEditorState())
    val state get() = _state.asStateFlow()

    fun onAction(action: MemeEditorAction) {
        when (action) {
            MemeEditorAction.OnAddTextClick -> {
                addText()
            }

            MemeEditorAction.OnConfirmAbortWithoutSave -> {
                TODO()
            }

            is MemeEditorAction.OnContainerSizeChange -> {
                updateContainerSize(action.size)
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
                unselectMemeText()
            }
        }
    }

    private fun unselectMemeText() {
        _state.update {
            it.copy(
                textBoxInteractionState = TextBoxInteractionState.None,
            )
        }
    }

    private fun addText() {
        val id = Uuid.random().toString()
        val memeText =
            MemeText(
                id = id,
                text = "tap to edit".uppercase(),
                offsetRatioX = CONTAINER_PADDING,
                offsetRatioY = CONTAINER_PADDING,
            )
        _state.update {
            it.copy(
                memeTexts = it.memeTexts + memeText,
                textBoxInteractionState = TextBoxInteractionState.Selected(id),
            )
        }
    }

    private fun updateContainerSize(size: IntSize) {
        _state.update {
            it.copy(
                templateSize = size,
            )
        }
    }

    private fun textTransformChange(
        textBoxId: TextBoxId,
        offset: Offset,
        scale: Float,
        rotation: Float,
    ) {
        _state.update {
            val (width, height) = it.templateSize
            it.copy(
                memeTexts =
                    it.memeTexts.map { memeText ->
                        if (memeText.id == textBoxId) {
                            memeText.copy(
                                offsetRatioX = offset.x * width,
                                offsetRatioY = offset.y * height,
                                scale = scale,
                                rotation = rotation,
                            )
                        } else {
                            memeText
                        }
                    },
            )
        }
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
