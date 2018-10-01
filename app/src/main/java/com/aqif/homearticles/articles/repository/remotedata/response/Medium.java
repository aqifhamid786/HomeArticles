
package com.aqif.homearticles.articles.repository.remotedata.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medium {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("mimeType")
    @Expose
    private String mimeType;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("size")
    @Expose
    private Object size;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

}
