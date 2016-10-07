package com.fedyanov.socialcontacts.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fedyanov.socialcontacts.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fistNameTextView) public TextView firstNameTextView;
    @BindView(R.id.lastNameTextView) public TextView lastNameTextView;
    @BindView(R.id.avatarImageView) public ImageView avatarImageView;
    @BindView(R.id.fbIcon) public View facebookIcon;
    @BindView(R.id.vkIcon) public View vkIcon;

    public ContactViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
