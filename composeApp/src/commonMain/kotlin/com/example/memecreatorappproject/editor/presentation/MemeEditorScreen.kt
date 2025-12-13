package com.example.memecreatorappproject.editor.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memecreatorappproject.core.presentation.MemeTemplate
import com.example.memecreatorappproject.core.theme.MemeCreatorTheme
import com.example.memecreatorappproject.editor.presentation.components.MemeTextBox
import memecreatorappproject.composeapp.generated.resources.Res
import memecreatorappproject.composeapp.generated.resources.meme_template_01
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemeEditorRoot(
    viewModel: MemeEditorViewModel = koinViewModel(),
    memeTemplate: MemeTemplate,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MemeEditorScreen(
        memeTemplate = memeTemplate,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun MemeEditorScreen(
    modifier: Modifier = Modifier,
    memeTemplate: MemeTemplate,
    state: MemeEditorState,
    onAction: (MemeEditorAction) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            painter = painterResource(memeTemplate.drawable),
            contentDescription = null,
        )
        state.memeTexts.forEach { memeText ->
            MemeTextBox(
                memeText = memeText,
                textBoxInteractionState = state.textBoxInteractionState,
//                test purpose only
                maxWidth = 500.dp,
//                test purpose only
                maxHeight = 500.dp,
                onClick = {
                    onAction(MemeEditorAction.OnSelectMemeText(textBoxId = memeText.id))
                },
                onDoubleClick = {
                    onAction(MemeEditorAction.OnEditMemeText(textBoxId = memeText.id))
                },
                onTextChange = {
                    onAction(MemeEditorAction.OnMemeTextChange(memeText.id, it))
                },
                onDeleteClick = {
                    onAction(MemeEditorAction.OnDeleteMemeTextClick(textBoxId = memeText.id))
                },
            )
        }
    }
}

@Preview
@Composable
fun PreviewMemeEditorScreen() {
    MemeCreatorTheme {
        MemeEditorScreen(
            memeTemplate =
                MemeTemplate(
                    id = "meme_template_01",
                    drawable = Res.drawable.meme_template_01,
                ),
            state = MemeEditorState(),
            onAction = {},
        )
    }
}
