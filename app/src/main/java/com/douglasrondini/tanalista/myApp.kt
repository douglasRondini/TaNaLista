package com.douglasrondini.tanalista

import android.app.Application
import com.douglasrondini.tanalista.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class myApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@myApp)
            modules(appModules)
        }
    }
}