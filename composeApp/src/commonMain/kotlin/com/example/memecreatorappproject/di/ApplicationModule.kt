package com.example.memecreatorappproject.di

import com.example.memecreatorappproject.editor.presentation.MemeEditorViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

val appModule =
    module {
        viewModel { MemeEditorViewModel(get(), get(), get()) }
        includes(platformModule)
    }
