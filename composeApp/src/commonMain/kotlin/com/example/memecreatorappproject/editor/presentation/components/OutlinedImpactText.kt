package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.memecreatorappproject.core.theme.MemeCreatorTheme
import com.example.memecreatorappproject.editor.presentation.util.rememberFillTextStyle
import com.example.memecreatorappproject.editor.presentation.util.rememberStrokeTextStyle
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OutlinedImpactText(
    modifier: Modifier = Modifier,
    text: String,
    strokeTextStyle: TextStyle,
    fillTextStyle: TextStyle,
) {
    Box(
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = strokeTextStyle,
        )
        Text(
            text = text,
            style = fillTextStyle,
        )
    }
}

@Preview
@Composable
fun PreviewOutlinedImpactText() {
    MemeCreatorTheme {
        OutlinedImpactText(
            text = "lorem ipsum".uppercase(),
            strokeTextStyle = rememberStrokeTextStyle(),
            fillTextStyle = rememberFillTextStyle(),
        )
    }
}
