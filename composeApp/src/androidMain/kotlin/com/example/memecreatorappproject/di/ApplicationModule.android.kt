package com.example.memecreatorappproject.di

import com.example.memecreatorappproject.editor.data.CacheStorageStrategy
import com.example.memecreatorappproject.editor.data.PlatformMemeExporter
import com.example.memecreatorappproject.editor.domain.MemeExporter
import com.example.memecreatorappproject.editor.domain.SaveToStorageStrategy
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module = module {

    //provide implementation covered by abstraction
    factoryOf(::PlatformMemeExporter) bind MemeExporter::class
    factoryOf(::CacheStorageStrategy) bind SaveToStorageStrategy::class
}