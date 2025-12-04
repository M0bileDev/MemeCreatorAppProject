package com.example.memecreatorappproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.memecreatorappproject.editor.presentation.MemeEditorRoot
import com.example.memecreatorappproject.gallery.presentation.MemeGalleryScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.MemeGallery,
    ) {
        composable<Route.MemeGallery> {
            MemeGalleryScreen(onClick = { memeTemplate ->
                navController.navigate(Route.MemeEditor(memeTemplate.id))
            })
        }
        composable<Route.MemeEditor> {
            val templateId = it.toRoute<Route.MemeEditor>().templateId
            val template =
                remember(templateId) { memeTemplates.first { (id, _) -> id == templateId } }
            MemeEditorRoot(memeTemplate = template)
        }
    }
}
