package com.fedyanov.socialcontacts.model;

import android.content.Intent;
import android.os.AsyncTask;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.fedyanov.socialcontacts.Constants;
import com.fedyanov.socialcontacts.callback.ItemLoadCallback;
import com.fedyanov.socialcontacts.callback.SocialLoginCallback;
import com.fedyanov.socialcontacts.model.entity.facebook.FBContact;
import com.fedyanov.socialcontacts.model.response.facebook.FBContactsResponse;
import com.fedyanov.socialcontacts.observer.ActivityEventObserver;
import com.fedyanov.socialcontacts.view.activity.BaseObservableEventActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
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

    public void getContacts(final ItemLoadCallback<List<FBContact>> contactItemLoadCallback) {
        new ContactsLoadingTask(contactItemLoadCallback).execute();
    }

    private class ContactsLoadingTask extends AsyncTask<Void, Void, List<FBContact>> {

        private ItemLoadCallback<List<FBContact>> loadCallback;
        private String error;

        public ContactsLoadingTask(ItemLoadCallback<List<FBContact>> loadCallback) {
            this.loadCallback = loadCallback;
        }

        @Override
        protected List<FBContact> doInBackground(Void... voids) {
            List<FBContact> contacts = new ArrayList<>();
            loadTaggableFriends(contacts);
            if (error != null)
                return null;
            loadAppFriends(contacts);
            if (error != null)
                return null;
            return contacts;
        }

        @Override
        protected void onPostExecute(List<FBContact> fbContacts) {
            super.onPostExecute(fbContacts);
            if (error == null) {
                loadCallback.onItemsLoaded(fbContacts);
            } else
                loadCallback.onLoadingError(error);
        }

        private void loadTaggableFriends(List<FBContact> contacts) {
            GraphResponse taggableResponse = getFriendsResponse("/me/taggable_friends");
            if (taggableResponse.getError() == null) {
                FBContactsResponse response = new Gson().fromJson(taggableResponse.getJSONObject().toString(), FBContactsResponse.class);
                contacts.addAll(response.contacts);
                while (response.paging != null && response.paging.next != null && error == null) {
                    taggableResponse = getFriendsResponse("/me/taggable_friends?limit=25&after=" +  response.paging.cursors.after);
                    if (taggableResponse.getError() == null) {
                        response = new Gson().fromJson(taggableResponse.getJSONObject().toString(), FBContactsResponse.class);
                        contacts.addAll(response.contacts);
                    } else {
                        error = taggableResponse.getError().getErrorMessage();
                    }
                }
            } else {
                error = taggableResponse.getError().getErrorMessage();
            }
        }

        private void loadAppFriends(List<FBContact> contacts) {
            GraphResponse friendsResponse = getFriendsResponse("/me/friends");
            if (friendsResponse.getError() == null) {
                FBContactsResponse response = new Gson().fromJson(friendsResponse.getJSONObject().toString(), FBContactsResponse.class);
                contacts.addAll(response.contacts);
                while (response.paging != null && response.paging.next != null && error == null) {
                    friendsResponse = getFriendsResponse("/me/friends?limit=25&after=" + response.paging.cursors.after);
                    if (friendsResponse.getError() == null) {
                        response = new Gson().fromJson(friendsResponse.getJSONObject().toString(), FBContactsResponse.class);
                        contacts.addAll(response.contacts);
                    } else {
                        error = friendsResponse.getError().getErrorMessage();
                    }
                }
            } else {
                error = friendsResponse.getError().getErrorMessage();
            }
        }

        private GraphResponse getFriendsResponse(String path) {
            return new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    path,
                    null,
                    HttpMethod.GET,
                    null
            ).executeAndWait();
        }
    }
}
