package com.fedyanov.socialcontacts.model.response.facebook;

import com.google.gson.annotations.SerializedName;

public class FBResponseCursors {
    @SerializedName("befrore")
    public String before;
    @SerializedName("after")
    public String after;
}
