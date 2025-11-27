@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memecreatorappproject.gallery.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.memecreatorappproject.core.presentation.MemeTemplate
import com.example.memecreatorappproject.core.presentation.memeTemplates
import memecreatorappproject.composeapp.generated.resources.Res
import memecreatorappproject.composeapp.generated.resources.hint_gallery_item_x
import memecreatorappproject.composeapp.generated.resources.meme_templates
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MemeGalleryScreen(
    modifier: Modifier = Modifier,
    onClick: (MemeTemplate) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(Res.string.meme_templates),
                    )
                },
            )
        },
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(innerPadding),
            columns = StaggeredGridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
        ) {
            items(
                items = memeTemplates,
                key = {
                    it.id
                },
            ) { memeTemplate ->
                Card(
                    onClick = { onClick(memeTemplate) },
                    shape = CardDefaults.elevatedShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painterResource(memeTemplate.drawable),
                        contentDescription =
                            stringResource(
                                Res.string.hint_gallery_item_x,
                                memeTemplate.id,
                            ),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
        }
    }
}
