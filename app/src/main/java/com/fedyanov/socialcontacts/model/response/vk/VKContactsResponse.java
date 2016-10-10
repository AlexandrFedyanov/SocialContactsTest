package com.fedyanov.socialcontacts.model.response.vk;

import com.google.gson.annotations.SerializedName;

public class VKContactsResponse {
    @SerializedName("response")
    public VKContactsResponseContent responseContent;
}
