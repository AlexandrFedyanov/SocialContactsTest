package com.fedyanov.socialcontacts.observer;

import android.content.Intent;

public interface ActivityEventObserver {
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
