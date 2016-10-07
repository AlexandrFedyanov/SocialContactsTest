package com.fedyanov.socialcontacts.presenter.presenterinterface;

import com.fedyanov.socialcontacts.view.viewinterface.SocialNetworksView;

public interface SocialNetworkPresenter {

    void attachView(SocialNetworksView view);

    void detachView();

    void onDestroy();

    void loginFB();

    void loginVK();
}
