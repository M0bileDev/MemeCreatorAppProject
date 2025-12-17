package com.example.memecreatorappproject.editor.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.memecreatorappproject.core.presentation.MemeTemplate
import com.example.memecreatorappproject.core.theme.MemeCreatorTheme
import com.example.memecreatorappproject.editor.presentation.components.BottomBar
import com.example.memecreatorappproject.editor.presentation.components.DraggableContainer
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
    Scaffold(
        modifier =
            modifier.fillMaxSize().pointerInput(Unit) {
                detectTapGestures {
                    onAction(MemeEditorAction.OnTapOutsideSelectedText)
                }
            },
        bottomBar = {
            BottomBar(
                onSaveClick = { onAction(MemeEditorAction.OnSaveMemeConfirm(memeTemplate)) },
                onAddTextClick = {
                    onAction(
                        MemeEditorAction.OnAddTextClick,
                    )
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box {
                Image(
                    modifier =
                        Modifier.fillMaxWidth().onSizeChanged {
                            onAction(MemeEditorAction.OnContainerSizeChange(it))
                        },
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(memeTemplate.drawable),
                    contentDescription = null,
                )
                DraggableContainer(
                    modifier = Modifier.matchParentSize(),
                    subComponents = state.memeTexts,
                    textBoxInteractionState = state.textBoxInteractionState,
                    onSubComponentClick = {
                        onAction(MemeEditorAction.OnSelectMemeText(textBoxId = it))
                    },
                    onSubComponentDoubleClick = {
                        onAction(MemeEditorAction.OnEditMemeText(textBoxId = it))
                    },
                    onSubComponentTextChange = { textBoxId, text ->
                        onAction(MemeEditorAction.OnMemeTextChange(textBoxId, text))
                    },
                    onSubComponentDeleteClick = {
                        onAction(MemeEditorAction.OnDeleteMemeTextClick(textBoxId = it))
                    },
                    onSubComponentTransformChange = { textBoxId, offset, rotation, scale ->
                        onAction(
                            MemeEditorAction.OnMemeTextTransformChange(
                                textBoxId,
                                offset,
                                rotation,
                                scale,
                            ),
                        )
                    },
                )
            }
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
