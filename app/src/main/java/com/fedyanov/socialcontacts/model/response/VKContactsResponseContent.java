package com.fedyanov.socialcontacts.model.response;

import com.fedyanov.socialcontacts.model.entity.VKContact;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VKContactsResponseContent {
    @SerializedName("count")
    public int itemsCount;
    @SerializedName("items")
    public List<VKContact> contacts;
}
