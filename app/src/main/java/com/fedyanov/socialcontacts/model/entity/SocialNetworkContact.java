package com.fedyanov.socialcontacts.model.entity;

import com.fedyanov.socialcontacts.model.NetworkType;
import com.google.gson.annotations.SerializedName;

public class SocialNetworkContact {
    @SerializedName("vk_id")
    public int vkId;
    @SerializedName("fb_id")
    public int fb_id;
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
        avatar = vkContact.smallAvatarl;
        network = NetworkType.VK;
    }
}
