package com.fedyanov.socialcontacts.model.response;

import com.google.gson.annotations.SerializedName;

public class VKContactsResponse {
    @SerializedName("response")
    public VKContactsResponseContent responseContent;
}
