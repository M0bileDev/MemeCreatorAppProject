package com.example.memecreatorappproject.editor.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MemeEditorViewModel : ViewModel() {
    private val _state: MutableStateFlow<MemeEditorState> = MutableStateFlow(MemeEditorState())
    val state get() = _state.asStateFlow()

    fun onAction(action: MemeEditorAction) {
        when (action) {
            MemeEditorAction.OnAddTextClick -> TODO()
            MemeEditorAction.OnConfirmAbortWithoutSave -> TODO()
            is MemeEditorAction.OnContainerSizeChange -> TODO()
            is MemeEditorAction.OnDeleteMemeTextClick -> TODO()
            MemeEditorAction.OnDismissAbortWithoutSave -> TODO()
            is MemeEditorAction.OnEditMemeText -> TODO()
            MemeEditorAction.OnGoBackClick -> TODO()
            is MemeEditorAction.OnMemeTextChange -> TODO()
            is MemeEditorAction.OnMemeTextTransformChange -> TODO()
            is MemeEditorAction.OnSaveMemeConfirm -> TODO()
            is MemeEditorAction.OnSelectMemeText -> TODO()
            MemeEditorAction.OnTapOutsideSelectedText -> TODO()
        }
    }
}
