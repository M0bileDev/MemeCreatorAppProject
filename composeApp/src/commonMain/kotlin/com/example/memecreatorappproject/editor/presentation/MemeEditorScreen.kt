package com.example.memecreatorappproject.editor.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemeEditorRoot(viewModel: MemeEditorViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MemeEditorScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun MemeEditorScreen(
    state: MemeEditorState,
    onAction: (MemeEditorAction) -> Unit,
) {
}
