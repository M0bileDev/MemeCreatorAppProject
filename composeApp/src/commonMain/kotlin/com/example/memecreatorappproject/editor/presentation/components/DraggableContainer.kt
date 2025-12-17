package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.example.memecreatorappproject.editor.presentation.MemeText
import com.example.memecreatorappproject.editor.presentation.TextBoxId
import com.example.memecreatorappproject.editor.presentation.TextBoxInteractionState

private const val MIN_SCALE = 0.5f
private const val MAX_SCALE = 2f

@Composable
fun DraggableContainer(
    modifier: Modifier = Modifier,
    subComponents: List<MemeText>,
    textBoxInteractionState: TextBoxInteractionState,
    onSubComponentTransformChange: (textId: TextBoxId, offset: Offset, rotation: Float, scale: Float) -> Unit,
    onSubComponentClick: (TextBoxId) -> Unit,
    onSubComponentDoubleClick: (TextBoxId) -> Unit,
    onSubComponentTextChange: (textBoxId: TextBoxId, text: String) -> Unit,
    onSubComponentDeleteClick: (TextBoxId) -> Unit,
) {
    val density = LocalDensity.current

    BoxWithConstraints {
        val containerWidth = constraints.maxWidth
        val containerHeight = constraints.maxHeight

        subComponents.forEach { component ->
            var componentWidth by
                remember(component.id) {
                    mutableStateOf(0)
                }
            var componentHeight by
                remember(component.id) {
                    mutableStateOf(0)
                }

            val transformableState =
                rememberTransformableState { zoomChange, panChange, rotationChange ->
                    val newScale = (component.scale * zoomChange).coerceIn(MIN_SCALE, MAX_SCALE)
                    val newOffset =
                        Offset(
                            x = component.offsetRatioX * containerWidth + panChange.x,
                            y = component.offsetRatioY * containerHeight + panChange.y,
                        )
                    val newRotation = component.rotation + rotationChange

                    onSubComponentTransformChange(component.id, newOffset, newRotation, newScale)
                }

            Box(
                modifier =
                    Modifier
                        .onSizeChanged {
                            componentWidth = it.width
                            componentHeight = it.height
                        }.graphicsLayer {
                            with(component) {
                                translationX = offsetRatioX * containerWidth
                                translationY = offsetRatioY * containerHeight
                                rotationZ = rotation
                                scaleX = scale
                                scaleY = scale
                            }
                        }.transformable(
                            transformableState,
                        ),
            ) {
                MemeTextBox(
                    memeText = component,
                    textBoxInteractionState = textBoxInteractionState,
                    maxWidth =
                        with(density) {
                            containerWidth.toDp()
                        },
                    maxHeight =
                        with(density) {
                            containerHeight.toDp()
                        },
                    onClick = {
                        onSubComponentClick(component.id)
                    },
                    onDoubleClick = {
                        onSubComponentDoubleClick(component.id)
                    },
                    onTextChange = {
                        onSubComponentTextChange(component.id, it)
                    },
                    onDeleteClick = {
                        onSubComponentDeleteClick(component.id)
                    },
                )
            }
        }
    }
}
