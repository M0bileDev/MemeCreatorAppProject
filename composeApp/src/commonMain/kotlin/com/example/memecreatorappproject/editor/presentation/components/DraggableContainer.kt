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
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

private const val MIN_SCALE = 0.5f
private const val MAX_SCALE = 2f

private const val DEGREES_PER_PI = 180f

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

    BoxWithConstraints(modifier) {
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

                    val newRotation = component.rotation + rotationChange
                    // rotate the translation of the pan change in order to move into right direction
                    //  1. calculate rotation in angle (not in degrees but radiance)
                    //  Radians: what sin(), cos(), atan2(), etc. use
                    val angle = newRotation * PI.toFloat() / DEGREES_PER_PI
                    //  2. calculate rotated pan value with cos and sin
                    val cos = cos(angle)
                    val sin = sin(angle)

                    // Translate drag after rotation
                    // Text rotated by 90 degrees, than text drag is translated to bottom drag
                    //
                    //
                    //  before rotation                         after rotation by 90 degrees
                    //  right drag move text                    drag to right needs still move
                    //  to right                                the meme text to right
                    //  -----top------                  |--right----|
                    //  |           |                   |           |
                    //  |           * <-                |           * <-
                    //  ----bottom---                   |--left-----|
                    //
                    //
                    val rotatedPanX = panChange.x * cos - panChange.y * sin
                    val rotatedPanY = panChange.x * sin - panChange.y * cos

                    val newScale = (component.scale * zoomChange).coerceIn(MIN_SCALE, MAX_SCALE)
                    // Constraint text inside main container

                    val scaledWidth = componentWidth * component.scale
                    val scaledHeight = componentHeight * component.scale

//                    Constraint meme text inside invisible bounding box
//
//                   | -------------------------------------|
//                   |      /=====================/         | <- invisible box
//                   |     /    text rotated     /          |
//                   |    /         by          /           |
//                   |   /      45 degrees     / <- visible box
//                   |  /                     /             |
//                   | /=====================/              |
//                   | -------------------------------------|
//
                    // projects rectangle edges on x and y axis
                    val visualWidth = abs(scaledWidth * cos) + abs(scaledHeight * sin)
                    val visualHeight = abs(scaledWidth * sin) + abs(scaledHeight * cos)

                    // Visible edges of the meme text
                    val scaleOffsetX = (scaledWidth - componentWidth) / 2
                    val scaleOffsetY = (scaledHeight - componentHeight) / 2

                    val rotationOffsetX = (visualWidth - scaledWidth) / 2
                    val rotationOffsetY = (visualHeight - scaledHeight) / 2

                    val newOffset =
                        Offset(
                            x = component.offsetRatioX * containerWidth + panChange.x,
                            y = component.offsetRatioY * containerHeight + panChange.y,
                        )

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
