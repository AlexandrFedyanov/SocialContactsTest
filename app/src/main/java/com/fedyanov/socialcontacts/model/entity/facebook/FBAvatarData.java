package com.fedyanov.socialcontacts.model.entity.facebook;

import com.google.gson.annotations.SerializedName;

public class FBAvatarData {
    @SerializedName("is_silhouette")
    public boolean isSilhouette;
    @SerializedName("url")
    public String url;
}
