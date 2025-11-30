package com.example.memecreatorappproject.di

import com.example.memecreatorappproject.editor.presentation.MemeEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModelOf<MemeEditorViewModel>(::MemeEditorViewModel)
    }
