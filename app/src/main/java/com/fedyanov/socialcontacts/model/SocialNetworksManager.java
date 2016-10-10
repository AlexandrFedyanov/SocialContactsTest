package com.fedyanov.socialcontacts.model;

import com.fedyanov.socialcontacts.SocialContactsApp;
import com.fedyanov.socialcontacts.callback.ItemLoadCallback;
import com.fedyanov.socialcontacts.callback.SocialLoginCallback;
import com.fedyanov.socialcontacts.model.entity.facebook.FBContact;
import com.fedyanov.socialcontacts.model.entity.vk.VKContact;
import com.fedyanov.socialcontacts.utils.ApplicationReceiver;
import com.fedyanov.socialcontacts.view.activity.BaseObservableEventActivity;

import java.util.List;

public class SocialNetworksManager {

    private ApplicationReceiver applicationReceiver;
    private FacebookSdkHelper facebookSdkHelper;
    private VKSdkHelper VKSdkHelper;

    public SocialNetworksManager(ApplicationReceiver applicationReceiver) {
        this.applicationReceiver = applicationReceiver;
        facebookSdkHelper = new FacebookSdkHelper();
        VKSdkHelper = new VKSdkHelper();
    }

    public void loginFacebook(SocialLoginCallback socialLoginCallback) {
        SocialContactsApp app = applicationReceiver.getApp();
        if (app != null && app.getForegroundActivity() != null && app.getForegroundActivity() instanceof BaseObservableEventActivity) {
            facebookSdkHelper.login((BaseObservableEventActivity) app.getForegroundActivity(), socialLoginCallback);
        } else {
            socialLoginCallback.onLoginFailed("");
        }
    }

    public void loginVK(SocialLoginCallback socialLoginCallback) {
        SocialContactsApp app = applicationReceiver.getApp();
        if (app != null && app.getForegroundActivity() != null && app.getForegroundActivity() instanceof BaseObservableEventActivity) {
            VKSdkHelper.login((BaseObservableEventActivity) app.getForegroundActivity(), socialLoginCallback);
        } else {
            socialLoginCallback.onLoginFailed("");
        }
    }

    public void getVKContacts(ItemLoadCallback<List<VKContact>> itemLoadCallback) {
        VKSdkHelper.getContacts(itemLoadCallback);
    }

    public void getFBContacts(ItemLoadCallback<List<FBContact>> itemLoadCallback) {
        facebookSdkHelper.getContacts(itemLoadCallback);
    }
}
