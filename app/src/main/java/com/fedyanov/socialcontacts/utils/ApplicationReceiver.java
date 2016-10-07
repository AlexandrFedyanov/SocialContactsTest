package com.fedyanov.socialcontacts.utils;

import com.fedyanov.socialcontacts.SocialContactsApp;

import java.lang.ref.WeakReference;

public class ApplicationReceiver {
    private WeakReference<SocialContactsApp> appWeakReference;

    public ApplicationReceiver(SocialContactsApp app) {
        appWeakReference = new WeakReference<SocialContactsApp>(app);
    }

    public SocialContactsApp getApp() {
        return appWeakReference.get();
    }
}
