package com.fedyanov.socialcontacts.model.response.facebook;

import com.fedyanov.socialcontacts.model.entity.facebook.FBContact;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FBContactsResponse {
    @SerializedName("data")
    public List<FBContact> contacts;
    @SerializedName("paging")
    public FBResponsePaging paging;
}