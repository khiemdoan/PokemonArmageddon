package com.daugau.armageddon;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by daugau on 1/11/18.
 */

public class PokemonArmageddonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
