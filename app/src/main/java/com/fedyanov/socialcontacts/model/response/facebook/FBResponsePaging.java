package com.fedyanov.socialcontacts.model.response.facebook;

import com.google.gson.annotations.SerializedName;

public class FBResponsePaging {
    @SerializedName("cursors")
    public FBResponseCursors cursors;
    @SerializedName("next")
    public String next;
}
