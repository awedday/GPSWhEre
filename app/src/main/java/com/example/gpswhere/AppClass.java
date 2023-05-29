package com.example.gpswhere;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class AppClass extends Application {

    public AppClass(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey("86b62060-f681-42c8-bbf8-7010ad40d4a6");
    }
}
