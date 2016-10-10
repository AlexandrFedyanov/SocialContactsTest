package com.fedyanov.socialcontacts.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.fedyanov.socialcontacts.R;
import com.fedyanov.socialcontacts.SocialContactsApp;
import com.fedyanov.socialcontacts.presenter.SocialNetworksPresenterImpl;
import com.fedyanov.socialcontacts.presenter.presenterinterface.SocialNetworkPresenter;
import com.fedyanov.socialcontacts.utils.ApplicationReceiver;
import com.fedyanov.socialcontacts.view.viewinterface.SocialNetworksView;

import butterknife.BindView;
import butterknife.OnClick;

public class SocialNetworksActivity extends BaseObservableEventActivity implements SocialNetworksView{

    @BindView(R.id.facebookButton) ImageView facebookButton;
    @BindView(R.id.vkButton) ImageView vkButton;

    private SocialNetworkPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_social_networks);
        presenter = new SocialNetworksPresenterImpl(new ApplicationReceiver(SocialContactsApp.getInstance()));
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.onDestroy();
    }

    @OnClick(R.id.facebookButton)
    public void loginFB() {
        presenter.loginFB();
    }

    @OnClick(R.id.vkButton)
    public void loginVK() {
        presenter.loginVK();
    }

    @OnClick(R.id.continueButton)
    public void onContinueClick() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setFbEnabled(boolean isEnabled) {
        facebookButton.setEnabled(isEnabled);
    }

    @Override
    public void setVkEnabled(boolean isEnabled) {
        vkButton.setEnabled(isEnabled);
    }
}
