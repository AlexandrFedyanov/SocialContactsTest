package com.fedyanov.socialcontacts.model.entity;

import com.fedyanov.socialcontacts.model.NetworkType;
import com.fedyanov.socialcontacts.model.entity.facebook.FBContact;
import com.fedyanov.socialcontacts.model.entity.vk.VKContact;
import com.google.gson.annotations.SerializedName;

public class SocialNetworkContact {
    @SerializedName("vk_id")
    public int vkId;
    @SerializedName("fb_id")
    public String fbId;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("network")
    public NetworkType network;

    public SocialNetworkContact() {}

    public SocialNetworkContact(VKContact vkContact) {
        vkId = vkContact.id;
        firstName = vkContact.firstName;
        lastName = vkContact.lastName;
        avatar = vkContact.avatar;
        network = NetworkType.VK;
    }

    public SocialNetworkContact(FBContact fbContact) {
        fbId = fbContact.id;
        String names[] = fbContact.name.split(" ");
        if (names.length == 2) {
            firstName = names[0];
            lastName = names[1];
        } else
            firstName = fbContact.name;
        avatar = fbContact.avatar.avatarData.url;
        network = NetworkType.FACEBOOK;
    }
}
