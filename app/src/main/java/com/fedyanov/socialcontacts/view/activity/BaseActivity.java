package com.fedyanov.socialcontacts.view.activity;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(android.R.id.content) View rootView;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void showMessage(String message) {
        CoordinatorLayout contentView = null;
        if (rootView != null && rootView instanceof ViewGroup
                && ((ViewGroup) rootView).getChildCount() > 0) {
            View contentViewGroup = ((ViewGroup) rootView).getChildAt(0);
            if (contentViewGroup instanceof CoordinatorLayout)
                contentView = (CoordinatorLayout) contentViewGroup;
        }
        if (contentView != null)
            Snackbar.make(contentView, message, Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
