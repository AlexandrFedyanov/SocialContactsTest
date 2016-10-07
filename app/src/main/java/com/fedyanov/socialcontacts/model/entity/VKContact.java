package com.fedyanov.socialcontacts.model.entity;

import com.google.gson.annotations.SerializedName;

public class VKContact {
    @SerializedName("id")
    public int id;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("photo_50")
    public String smallAvatarl;
}
