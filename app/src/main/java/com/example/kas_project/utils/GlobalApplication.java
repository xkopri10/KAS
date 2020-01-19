package com.example.kas_project.utils;

import android.app.Application;
import android.content.Context;

/**
 * Class for getting application context (needed in fragments)
 */
public class GlobalApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }

}
