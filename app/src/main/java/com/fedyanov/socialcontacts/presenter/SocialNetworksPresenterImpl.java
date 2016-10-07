package com.fedyanov.socialcontacts.presenter;

import com.fedyanov.socialcontacts.callback.SocialLoginCallback;
import com.fedyanov.socialcontacts.model.SocialNetworksManager;
import com.fedyanov.socialcontacts.presenter.presenterinterface.SocialNetworkPresenter;
import com.fedyanov.socialcontacts.utils.ApplicationReceiver;
import com.fedyanov.socialcontacts.utils.PreferenceHelper;
import com.fedyanov.socialcontacts.view.viewinterface.SocialNetworksView;

public class SocialNetworksPresenterImpl extends BasePresenter<SocialNetworksView> implements SocialNetworkPresenter {

    private SocialNetworksManager socialNetworksManager;
    private PreferenceHelper preferenceHelper;

    public SocialNetworksPresenterImpl(ApplicationReceiver applicationReceiver) {
        socialNetworksManager = new SocialNetworksManager(applicationReceiver);
        preferenceHelper = new PreferenceHelper(applicationReceiver.getApp());
    }

    @Override
    public void attachView(SocialNetworksView view) {
        super.attachView(view);
        view.setFbEnabled(!preferenceHelper.isFbLogged());
        view.setVkEnabled(!preferenceHelper.isVkLogged());
    }

    @Override
    public void onDestroy() {
        preferenceHelper.cleanUp();
        socialNetworksManager = null;
        preferenceHelper = null;
    }

    @Override
    public void loginFB() {
        socialNetworksManager.loginFacebook(new SocialLoginCallback() {
            @Override
            public void onLoginSuccess() {
                if (view != null) {
                    view.setFbEnabled(false);
                    view.showMessage("fb connected!");
                }
            }

            @Override
            public void onLoginFailed(String message) {
                if (view != null) {
                    view.showMessage(message);
                }
            }
        });
    }

    @Override
    public void loginVK() {
        socialNetworksManager.loginVK(new SocialLoginCallback() {
            @Override
            public void onLoginSuccess() {
                if (view != null) {
                    view.setVkEnabled(false);
                    view.showMessage("vk connected!");
                }
            }

            @Override
            public void onLoginFailed(String message) {
                if (view != null) {
                    view.showMessage(message);
                }
            }
        });
    }
}
