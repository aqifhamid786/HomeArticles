
package com.aqif.homearticles.articles.repository.remotedata.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("media")
    @Expose
    private List<Medium> media = null;

    @SerializedName("sku")
    @Expose
    private String sku;

    @SerializedName("title")
    @Expose
    private String title;

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
