package com.fedyanov.socialcontacts.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_social_networks) {
            showSocialNetworksScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void setupLayout() {
        contactsAdapter = new ContactsAdapter();
        contactsListView.setAdapter(contactsAdapter);
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

    }

    @Override
    public void showSocialNetworksScreen() {
        startActivity(new Intent(this, SocialNetworksActivity.class));
    }
}
