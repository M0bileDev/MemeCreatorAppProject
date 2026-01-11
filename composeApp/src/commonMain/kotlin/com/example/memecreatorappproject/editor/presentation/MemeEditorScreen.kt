package com.example.memecreatorappproject.editor.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.memecreatorappproject.editor.presentation.components.ConfirmationDialog
import com.example.memecreatorappproject.editor.presentation.components.ConfirmationDialogConfig
import com.example.memecreatorappproject.editor.presentation.components.DraggableContainer
import memecreatorappproject.composeapp.generated.resources.Res
import memecreatorappproject.composeapp.generated.resources.cancel
import memecreatorappproject.composeapp.generated.resources.leave
import memecreatorappproject.composeapp.generated.resources.leave_editor_message
import memecreatorappproject.composeapp.generated.resources.leave_editor_title
import memecreatorappproject.composeapp.generated.resources.meme_template_01
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MemeEditorRoot(
    viewModel: MemeEditorViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    memeTemplate: MemeTemplate,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isEditMode) {
        if (!state.isEditMode) {
            onNavigateBack()
        }
    }

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
            IconButton(
                modifier = Modifier.align(Alignment.TopStart),
                onClick = { onAction(MemeEditorAction.OnGoBackClick) },
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowLeft, contentDescription = "Back")
            }
        }
    }
    if (state.abortWithoutSave) {
        ConfirmationDialog(
            config =
                ConfirmationDialogConfig(
                    title = stringResource(Res.string.leave_editor_title),
                    message = stringResource(Res.string.leave_editor_message),
                    confirmButtonText = stringResource(Res.string.leave),
                    dismissButtonText = stringResource(Res.string.cancel),
                    confirmButtonColor = MaterialTheme.colorScheme.secondary,
                ),
            onConfirmAction = {
                onAction(MemeEditorAction.OnConfirmAbortWithoutSave)
            },
            onDismissAction = {
                onAction(MemeEditorAction.OnDismissAbortWithoutSave)
            },
        )
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
