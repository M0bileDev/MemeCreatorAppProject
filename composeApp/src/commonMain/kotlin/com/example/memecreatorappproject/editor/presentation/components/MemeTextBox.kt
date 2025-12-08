package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.TextBoxInteractionState
import com.example.memecreatorappproject.editor.presentation.isFocused

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
    Box(modifier) {
        Box(
            modifier =
                Modifier
                    .sizeIn(
                        maxWidth = maxWidth,
                        maxHeight = maxHeight,
                    ).border(
                        width = 2.dp,
                        color = if (textBoxInteractionState.isFocused()) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(4.dp),
                    ).background(
                        color =
                            if (textBoxInteractionState is TextBoxInteractionState.Editing) {
                                Color.Black.copy(
                                    alpha = 0.15f,
                                )
                            } else {
                                Color.Transparent
                            },
                        shape = RoundedCornerShape(4.dp),
                    ).combinedClickable(
                        onClick = onClick,
                        onDoubleClick = onDoubleClick,
                    ),
        ) {
            val textPadding = 16.dp
            val borderPadding = textPadding / 2
            if (textBoxInteractionState is TextBoxInteractionState.Editing) {
                OutlinedImpactTextField(
                    modifier = Modifier.padding(borderPadding),
                    text = memeText.text,
                    onTextChange = onTextChange,
                    maxWidth = maxWidth - textPadding,
                    maxHeight = maxWidth - textPadding,
                )
            } else {
                OutlinedImpactText(
                    modifier = Modifier.padding(borderPadding),
                    text = memeText.text,
                )
            }
        }
        if (textBoxInteractionState.isFocused()) {
            val boxSize = 24.dp
            Box(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .size(boxSize)
                        .offset(x = (boxSize / 2), y = -(boxSize / 2))
                        .clip(CircleShape)
                        .background(color = Color.Red.copy(alpha = 0.9f))
                        .clickable(onClick = onDeleteClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    }
}
