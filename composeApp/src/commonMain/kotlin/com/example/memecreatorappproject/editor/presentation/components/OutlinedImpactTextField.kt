package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.example.memecreatorappproject.editor.presentation.util.rememberFillTextStyle
import com.example.memecreatorappproject.editor.presentation.util.rememberStrokeTextStyle

@Composable
fun OutlinedImpactTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    strokeTextStyle: TextStyle = rememberStrokeTextStyle(),
    fillTextStyle: TextStyle = rememberFillTextStyle(),
    maxWidth: Dp? = null,
    maxHeight: Dp? = null,
) {
//    Object's responsibility to take a text and measure it's bounds, how much space text will be occupied
    val measurer = rememberTextMeasurer()
    val constraints = calculateTextConstraints(maxWidth, maxHeight)
    val textLayoutResult =
        remember(text, strokeTextStyle, constraints) {
            measurer.measure(text = text, style = strokeTextStyle, constraints = constraints)
        }
    val textBoundingSize =
        with(LocalDensity.current) {
            DpSize(
                width = textLayoutResult.size.width.toDp(),
                height = textLayoutResult.size.height.toDp(),
            )
        }

    BasicTextField(
        modifier = modifier.size(textBoundingSize),
        value = text,
        onValueChange = { text -> onTextChange(text.uppercase()) },
        cursorBrush =
            SolidColor(
                Color.White,
            ),
        singleLine = false,
        textStyle = fillTextStyle,
        decorationBox = { innerTextField ->
            Text(
                text = text,
                style = strokeTextStyle,
            )
            // On the text with style of stroke, will be placed text with fill style
            innerTextField()
        },
    )
}

/**
 * Calculate text constraints -> 2d space in which composable object can move
 */
@Composable
private fun calculateTextConstraints(
    maxWidth: Dp?,
    maxHeight: Dp?,
): Constraints =
    with(LocalDensity.current) {
        return Constraints(
            maxWidth = maxWidth?.roundToPx() ?: Int.MAX_VALUE,
            maxHeight = maxHeight?.roundToPx() ?: Int.MAX_VALUE,
        )
    }
