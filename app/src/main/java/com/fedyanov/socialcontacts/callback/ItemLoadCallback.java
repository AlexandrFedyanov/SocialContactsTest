package com.fedyanov.socialcontacts.callback;

public interface ItemLoadCallback<T> {

    void onItemsLoaded(T items);

    void onLoadingError(String errorMessage);
}
