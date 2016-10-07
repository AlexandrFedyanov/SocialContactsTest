package com.fedyanov.socialcontacts;

import android.app.Activity;
import android.app.Application;

import com.facebook.FacebookSdk;
import com.fedyanov.socialcontacts.utils.LifecycleHandler;
import com.vk.sdk.VKSdk;

public class SocialContactsApp extends Application {

    private static SocialContactsApp instance;

    private Activity foregroundActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FacebookSdk.sdkInitialize(this);
        VKSdk.initialize(this);
        registerActivityLifecycleCallbacks(new LifecycleHandler());
    }

    public static SocialContactsApp getInstance () {
        return instance;
    }

    public void setForegroundActivity(Activity activity) {
        this.foregroundActivity = activity;
    }

    public Activity getForegroundActivity() {
        return foregroundActivity;
    }
}
