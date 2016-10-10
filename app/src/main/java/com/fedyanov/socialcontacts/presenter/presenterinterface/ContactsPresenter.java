package com.fedyanov.socialcontacts.presenter.presenterinterface;

import com.fedyanov.socialcontacts.view.viewinterface.ContactsView;

public interface ContactsPresenter {
    void attachView(ContactsView view);

    void detachView();

    void onDestroy();

    void getContacts();

    boolean isLoading();
}
