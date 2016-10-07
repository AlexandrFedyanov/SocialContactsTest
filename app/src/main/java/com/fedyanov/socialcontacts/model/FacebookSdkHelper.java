package com.fedyanov.socialcontacts.model;

import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fedyanov.socialcontacts.Constants;
import com.fedyanov.socialcontacts.callback.ItemLoadCallback;
import com.fedyanov.socialcontacts.callback.SocialLoginCallback;
import com.fedyanov.socialcontacts.model.entity.VKContact;
import com.fedyanov.socialcontacts.observer.ActivityEventObserver;
import com.fedyanov.socialcontacts.view.activity.BaseObservableEventActivity;

import java.util.Arrays;
import java.util.List;

public class FacebookSdkHelper {

    private CallbackManager callbackManager;

    public FacebookSdkHelper() {
        callbackManager = CallbackManager.Factory.create();
    }

    public void login(final BaseObservableEventActivity activity, final SocialLoginCallback loginCallback) {
        activity.registerObserver(new ActivityEventObserver() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
                activity.removeObserver(this);
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginCallback.onLoginSuccess();
            }

            @Override
            public void onCancel() {
                loginCallback.onLoginFailed("canceled");
            }

            @Override
            public void onError(FacebookException error) {
                loginCallback.onLoginFailed(error.getMessage());
            }
        });
        LoginManager.getInstance()
                .logInWithReadPermissions(activity, Arrays.asList(Constants.SocialNetworksPermission.fb_read_contacts));

    }
}
