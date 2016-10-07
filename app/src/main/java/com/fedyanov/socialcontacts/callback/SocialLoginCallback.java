package com.fedyanov.socialcontacts.callback;

public interface SocialLoginCallback {
    void onLoginSuccess();
    void onLoginFailed(String message);
}
