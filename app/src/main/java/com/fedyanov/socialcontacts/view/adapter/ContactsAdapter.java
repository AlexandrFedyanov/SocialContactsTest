package com.fedyanov.socialcontacts.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fedyanov.socialcontacts.R;
import com.fedyanov.socialcontacts.model.NetworkType;
import com.fedyanov.socialcontacts.model.entity.SocialNetworkContact;
import com.fedyanov.socialcontacts.view.adapter.viewholder.ContactViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    private List<SocialNetworkContact> contacts = new ArrayList<>();

    private WeakReference<Context> contextWeakReference;

    public ContactsAdapter(Context context) {
        contextWeakReference = new WeakReference<Context>(context);
    }

    public void setData(List<SocialNetworkContact> data) {
        this.contacts = data;
        notifyDataSetChanged();
    }

    public void clear() {
        contacts.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private SocialNetworkContact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        SocialNetworkContact contact = getItem(position);
        holder.firstNameTextView.setText(contact.firstName);
        holder.lastNameTextView.setText(contact.lastName);
        if (contact.avatar != null && !contact.avatar.isEmpty()) {
            Glide.with(contextWeakReference.get())
                    .load(contact.avatar)
                    .error(R.drawable.ic_no_avatar)
                    .placeholder(R.drawable.ic_no_avatar)
                    .into(holder.avatarImageView);
        } else
            holder.avatarImageView.setImageResource(R.drawable.ic_no_avatar);
        switch (contact.network) {
            case FACEBOOK:
                holder.facebookIcon.setVisibility(View.VISIBLE);
                holder.vkIcon.setVisibility(View.GONE);
                break;
            case VK:
                holder.facebookIcon.setVisibility(View.GONE);
                holder.vkIcon.setVisibility(View.VISIBLE);
                break;
            case BOUTH:
                holder.facebookIcon.setVisibility(View.VISIBLE);
                holder.vkIcon.setVisibility(View.VISIBLE);
                break;
        }
    }

}
