package com.fedyanov.socialcontacts.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.fedyanov.socialcontacts.Constants;
import com.fedyanov.socialcontacts.SocialContactsApp;
import com.vk.sdk.VKSdk;

public class PreferenceHelper {

    private Context context;

    public PreferenceHelper(Context context) {
        this.context = context;
    }

    public void cleanUp() {
        context = null;
    }

    public boolean isFirstLaunch() {
        return getPreferences().getBoolean(Constants.Prefenence.KEY_FIRST_LAUNCH, true);
    }

    public void setIsFirstLaunch(boolean isFirstLaunch) {
        getPreferences().edit().putBoolean(Constants.Prefenence.KEY_FIRST_LAUNCH, isFirstLaunch).apply();
    }

    private SharedPreferences getPreferences() {
        return SocialContactsApp.getInstance().getSharedPreferences(Constants.Prefenence.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFbLogged() {
        return Profile.getCurrentProfile() != null && AccessToken.getCurrentAccessToken() != null;
    }

    public boolean isVkLogged() {
        return VKSdk.isLoggedIn();
    }
}
