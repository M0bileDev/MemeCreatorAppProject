package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.TextBoxInteractionState

@Composable
fun MemeTextBox(
    modifier: Modifier = Modifier,
    memeText: MemeText,
    onTextChange: (String) -> Unit,
    textBoxInteractionState: TextBoxInteractionState,
    maxWidth: Dp,
    maxHeight: Dp,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
}
