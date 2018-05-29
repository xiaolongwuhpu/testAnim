package com.testanim.longwu;

import android.app.Application;

public class BaseApplication extends Application {

    private static BaseApplication mApplicationContext;

    public static BaseApplication getInstance() {
        if (mApplicationContext == null) {
            mApplicationContext = new BaseApplication();
        }
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
    }
}
