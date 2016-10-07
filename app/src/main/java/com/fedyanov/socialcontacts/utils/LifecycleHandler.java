package com.fedyanov.socialcontacts.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.fedyanov.socialcontacts.SocialContactsApp;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class LifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private int resumed;
    private int started;
    private int created;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ++created;

    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
        SocialContactsApp.getInstance().setForegroundActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        --resumed;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        --started;

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        --created;
        if (isCloseApp())
            SocialContactsApp.getInstance().setForegroundActivity(null);
    }

    private boolean isCloseApp() {
        return created == 0;
    }

    public boolean isForeground() {
        return !isCloseApp() && started > 0;
    }

    public boolean isBackground() {
        return !isCloseApp() && started == 0 && resumed == 0;
    }

    public boolean isPaused() {
        return !isCloseApp() && resumed == 0;
    }
}