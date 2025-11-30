package com.example.memecreatorappproject

import android.app.Application
import com.example.memecreatorappproject.di.initKoin
import org.koin.android.ext.koin.androidContext

class MemeCreatorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MemeCreatorApp)
        }
    }
}
