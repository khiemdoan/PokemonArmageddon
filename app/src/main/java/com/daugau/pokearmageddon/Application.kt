package com.daugau.pokearmageddon

import androidx.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary

class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
    }
}