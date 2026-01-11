package com.example.memecreatorappproject.editor.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.memecreatorappproject.core.theme.MemeCreatorTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onAddTextClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
    ) {
        OutlinedButton(onClick = onAddTextClick) {
            Text("Add text")
        }
        Button(
            onClick = onSaveClick,
            colors = ButtonDefaults.buttonColors(contentColor = Color.White)
        ) {
            Text("Save meme")
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    MemeCreatorTheme {
        BottomBar(onAddTextClick = {}, onSaveClick = {})
    }
}
