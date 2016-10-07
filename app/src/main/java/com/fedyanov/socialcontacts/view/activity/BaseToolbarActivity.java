package com.fedyanov.socialcontacts.view.activity;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fedyanov.socialcontacts.R;

import butterknife.BindView;

public abstract class BaseToolbarActivity extends BaseObservableEventActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
        setupLayout();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    abstract void setupLayout();
}
