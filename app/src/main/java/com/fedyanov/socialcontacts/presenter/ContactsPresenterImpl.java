package com.fedyanov.socialcontacts.presenter;

import com.fedyanov.socialcontacts.callback.ItemLoadCallback;
import com.fedyanov.socialcontacts.model.NetworkType;
import com.fedyanov.socialcontacts.model.SocialNetworksManager;
import com.fedyanov.socialcontacts.model.entity.facebook.FBContact;
import com.fedyanov.socialcontacts.model.entity.SocialNetworkContact;
import com.fedyanov.socialcontacts.model.entity.vk.VKContact;
import com.fedyanov.socialcontacts.presenter.presenterinterface.ContactsPresenter;
import com.fedyanov.socialcontacts.utils.ApplicationReceiver;
import com.fedyanov.socialcontacts.utils.PreferenceHelper;
import com.fedyanov.socialcontacts.view.viewinterface.ContactsView;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenterImpl extends BasePresenter<ContactsView> implements ContactsPresenter {

    private SocialNetworksManager socialNetworksManager;
    private PreferenceHelper preferenceHelper;
    private List<SocialNetworkContact> contacts = new ArrayList<>();
    private List<SocialNetworkContact> refreshedContacts = new ArrayList<>();
    private int runningRequests = 0;
    private boolean isRefreshing = false;

    public ContactsPresenterImpl(ApplicationReceiver applicationReceiver) {
        socialNetworksManager = new SocialNetworksManager(applicationReceiver);
        preferenceHelper = new PreferenceHelper(applicationReceiver.getApp());
        if (!isLoading() && (preferenceHelper.isFbLogged() || preferenceHelper.isVkLogged()))
            getContacts();
    }

    @Override
    public void attachView(ContactsView view) {
        super.attachView(view);
        if (preferenceHelper.isFirstLaunch() &&  !preferenceHelper.isVkLogged() && !preferenceHelper.isFbLogged()) {
            preferenceHelper.setIsFirstLaunch(false);
            view.showSocialNetworksScreen();
        } else if (!preferenceHelper.isVkLogged() && !preferenceHelper.isFbLogged()) {
            view.setNoSocialNetworksState();
        } else {
            view.setFBConnected(preferenceHelper.isFbLogged());
            view.setVKConnected(preferenceHelper.isVkLogged());
            view.setRefreshing(isLoading());
            if (contacts.size() > 0)
                view.setContacts(contacts);
            if (contacts.size() == 0 && !isLoading())
                getContacts();
        }
    }

    @Override
    public void onDestroy() {
        preferenceHelper.cleanUp();
        socialNetworksManager = null;
        preferenceHelper = null;
    }

    @Override
    public void getContacts() {
        if (contacts.size() > 0) {
            isRefreshing = true;
            refreshedContacts.clear();
        }
        if (preferenceHelper.isVkLogged()) {
            addRequest();
            socialNetworksManager.getVKContacts(new ItemLoadCallback<List<VKContact>>() {
                @Override
                public void onItemsLoaded(List<VKContact> items) {
                    addVKContacts(items);
                }

                @Override
                public void onLoadingError(String errorMessage) {
                    showSynchronizationError();
                }
            });
        }
        if (preferenceHelper.isFbLogged()) {
            addRequest();
            socialNetworksManager.getFBContacts(new ItemLoadCallback<List<FBContact>>() {
                @Override
                public void onItemsLoaded(List<FBContact> items) {
                    addFBContacts(items);
                }

                @Override
                public void onLoadingError(String errorMessage) {
                    showSynchronizationError();
                }
            });
        }

    }

    private void addVKContacts(List<VKContact> vkContacts) {
        for (VKContact vkContact : vkContacts) {
            SocialNetworkContact contact = new SocialNetworkContact(vkContact);
            addContact(contact);
        }
        completeRequest();
        if (!isLoading() && isRefreshing) {
            contacts.clear();
            contacts.addAll(refreshedContacts);
        }
        if (view != null)
            view.setContacts(contacts);
    }

    private void addFBContacts(List<FBContact> fbContacts) {
        for (FBContact fbContact : fbContacts) {
            SocialNetworkContact contact = new SocialNetworkContact(fbContact);
            addContact(contact);
        }
        completeRequest();
        if (!isLoading() && isRefreshing) {
            contacts.clear();
            contacts.addAll(refreshedContacts);
        }
        if (view != null)
            view.setContacts(contacts);
    }

    private void showSynchronizationError() {
        completeRequest();
    }

    private synchronized void addRequest() {
        runningRequests++;
        if (view != null)
            view.setRefreshing(isLoading());
    }

    private synchronized void completeRequest() {
        runningRequests--;
        if (view != null)
            view.setRefreshing(isLoading());
    }

    private synchronized void addContact(SocialNetworkContact newContact) {
        boolean sameContact = false;
        List<SocialNetworkContact> currentLoadingContacts;
        if (isRefreshing)
            currentLoadingContacts = refreshedContacts;
        else
            currentLoadingContacts = contacts;
        for (SocialNetworkContact contact : currentLoadingContacts) {
            boolean sameFirsName = false;
            boolean sameLastName = false;
            if (contact.firstName != null && contact.firstName.equals(newContact.firstName))
                sameFirsName = true;
            if (contact.lastName != null && contact.lastName.equals(newContact.lastName))
                sameLastName = true;
            if (sameFirsName && sameLastName) {
                sameContact = true;
                contact.network = NetworkType.BOUTH;
                if (contact.fbId == null)
                    contact.fbId = newContact.fbId;
                if (contact.vkId == 0)
                    contact.vkId = newContact.vkId;
                if (contact.phone == null)
                    contact.phone = newContact.phone;
                if (contact.email == null)
                    contact.email = newContact.email;
            }
        }
        if (!sameContact && !isRefreshing)
            contacts.add(newContact);
        else if (!sameContact)
            refreshedContacts.add(newContact);
    }

    public synchronized boolean isLoading() {
        return runningRequests != 0;
    }
}
