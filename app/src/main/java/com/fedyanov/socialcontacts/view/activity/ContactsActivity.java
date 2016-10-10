package com.fedyanov.socialcontacts.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fedyanov.socialcontacts.R;
import com.fedyanov.socialcontacts.SocialContactsApp;
import com.fedyanov.socialcontacts.model.entity.SocialNetworkContact;
import com.fedyanov.socialcontacts.presenter.ContactsPresenterImpl;
import com.fedyanov.socialcontacts.presenter.presenterinterface.ContactsPresenter;
import com.fedyanov.socialcontacts.utils.ApplicationReceiver;
import com.fedyanov.socialcontacts.view.adapter.ContactsAdapter;
import com.fedyanov.socialcontacts.view.viewinterface.ContactsView;

import java.util.List;

import butterknife.BindView;

public class ContactsActivity extends BaseToolbarActivity implements ContactsView{

    @BindView(R.id.contactsList) RecyclerView contactsListView;
    private ContactsAdapter contactsAdapter;
    private ContactsPresenter presenter;
    private ImageView refreshImageView;
    private MenuItem refreshMenuItem;
    private Animation refreshAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        presenter = new ContactsPresenterImpl(new ApplicationReceiver(SocialContactsApp.getInstance()));
    }

    @Override
    protected void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.detachView();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        refreshMenuItem = menu.findItem(R.id.refresh);
        setRefreshing(presenter.isLoading());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_social_networks) {
            showSocialNetworksScreen();
            return true;
        } else if (item.getItemId() == R.id.refresh) {
            presenter.getContacts();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void setupLayout() {
        contactsAdapter = new ContactsAdapter(this);
        contactsListView.setAdapter(contactsAdapter);
        refreshImageView = (ImageView)((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_refresh, null);
        refreshAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        refreshAnimation.setRepeatCount(Animation.INFINITE);

    }

    @Override
    public void setNoSocialNetworksState() {

    }

    @Override
    public void setFBConnected(boolean isConnected) {

    }

    @Override
    public void setVKConnected(boolean isConnected) {

    }

    @Override
    public void setContacts(List<SocialNetworkContact> contacts) {
        contactsAdapter.setData(contacts);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        if (refreshMenuItem == null)
            return;
        if (isRefreshing) {
            refreshImageView.startAnimation(refreshAnimation);
            refreshMenuItem.setActionView(refreshImageView);
        } else if (refreshMenuItem.getActionView() != null){
            refreshMenuItem.getActionView().clearAnimation();
            refreshMenuItem.setActionView(null);
        }
    }

    @Override
    public void showSocialNetworksScreen() {
        startActivity(new Intent(this, SocialNetworksActivity.class));
    }
}
