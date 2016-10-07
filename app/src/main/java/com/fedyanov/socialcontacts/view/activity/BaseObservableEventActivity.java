package com.fedyanov.socialcontacts.view.activity;

import android.content.Intent;

import com.fedyanov.socialcontacts.observer.ActivityEventObserver;

import java.util.ArrayList;
import java.util.List;

public class BaseObservableEventActivity extends BaseActivity{
    private List<ActivityEventObserver> observers = new ArrayList<>();

    public void registerObserver(ActivityEventObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ActivityEventObserver observer) {
        observers.remove(observer);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<ActivityEventObserver> copiedObserversList = new ArrayList<>(observers); //avoid ConcurrentModificationException if observer is removing after event
        for (ActivityEventObserver observer : copiedObserversList)
            observer.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        observers.clear();
        super.onDestroy();
    }
}
