package com.fedyanov.socialcontacts.view.viewinterface;

import com.fedyanov.socialcontacts.model.entity.SocialNetworkContact;

import java.util.List;

public interface ContactsView {

    void setNoSocialNetworksState();

    void setFBConnected(boolean isConnected);

    void setVKConnected(boolean isConnected);

    void setContacts(List<SocialNetworkContact> contacts);

    void setRefreshing(boolean isRefreshing);

    void showMessage(String message);

    void showSocialNetworksScreen();
}
