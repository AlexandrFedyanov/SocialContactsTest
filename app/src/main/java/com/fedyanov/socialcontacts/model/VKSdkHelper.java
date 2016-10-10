package com.fedyanov.socialcontacts.model;

import android.content.Intent;

import com.fedyanov.socialcontacts.Constants;
import com.fedyanov.socialcontacts.callback.ItemLoadCallback;
import com.fedyanov.socialcontacts.callback.SocialLoginCallback;
import com.fedyanov.socialcontacts.model.entity.vk.VKContact;
import com.fedyanov.socialcontacts.model.response.vk.VKContactsResponse;
import com.fedyanov.socialcontacts.observer.ActivityEventObserver;
import com.fedyanov.socialcontacts.view.activity.BaseObservableEventActivity;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VKSdkHelper {


    public void login(final BaseObservableEventActivity activity, final SocialLoginCallback loginCallback) {
        activity.registerObserver(new ActivityEventObserver() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
                    @Override
                    public void onResult(VKAccessToken res) {
                        loginCallback.onLoginSuccess();
                    }
                    @Override
                    public void onError(VKError error) {
                        loginCallback.onLoginFailed(error.errorMessage);
                    }
                }))
                activity.removeObserver(this);
            }
        });
        VKSdk.login(activity, Constants.SocialNetworksPermission.vk_read_contacts);
    }

    public void getContacts(final ItemLoadCallback<List<VKContact>> contactItemLoadCallback) {
        Map<String, Object> vkParameters = new HashMap<>();
        vkParameters.put("fields", "nickname,photo_50,");
        VKRequest request = VKApi.friends().get(new VKParameters(vkParameters));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Gson gson = new Gson();
                try {
                    VKContactsResponse contactsResponse = gson.fromJson(response.responseString, VKContactsResponse.class);
                    contactItemLoadCallback.onItemsLoaded(contactsResponse.responseContent.contacts);
                } catch (JsonParseException e) {
                    contactItemLoadCallback.onLoadingError(e.getMessage());
                }
                super.onComplete(response);
            }

            @Override
            public void onError(VKError error) {
                contactItemLoadCallback.onLoadingError(error.errorMessage);
                super.onError(error);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }
        });
    }
}
