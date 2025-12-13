package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.TextBoxId
import com.example.memecreatorappproject.editor.presentation.TextBoxInteractionState
import com.example.memecreatorappproject.editor.presentation.TextId

@Composable
fun DraggableContainer(
    modifier: Modifier = Modifier,
    subComponents: List<MemeText>,
    textBoxInteractionState: TextBoxInteractionState,
    onSubComponentTransformChange: (textId: TextId, offset: Offset, rotation: Float, scale: Float) -> Unit,
    onSubComponentClick: (TextId) -> Unit,
    onSubComponentDoubleClick: (TextId) -> Unit,
    onSubComponentTextChange: (textBoxId: TextBoxId, text: String) -> Unit,
    onSubComponentDeleteClick: (TextId) -> Unit,
) {
}
