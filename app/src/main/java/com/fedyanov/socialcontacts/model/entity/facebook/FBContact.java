package com.fedyanov.socialcontacts.model.entity.facebook;

import com.google.gson.annotations.SerializedName;

public class FBContact {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("picture")
    public FBAvatar avatar;
}
