
package com.aqif.homearticles.articles.repository.remotedata.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticlesResponse {

    @SerializedName("resourceType")
    @Expose
    private String resourceType;
    @SerializedName("articlesCount")
    @Expose
    private Integer articlesCount;
    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(Integer articlesCount) {
        this.articlesCount = articlesCount;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

}
