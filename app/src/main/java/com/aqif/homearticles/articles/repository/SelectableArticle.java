package com.aqif.homearticles.articles.repository;

import com.aqif.homearticles.articles.repository.remotedata.response.Article;

/**
 * Wraps up article entity to assosiate selection information with them. (Whether user likes this article or not?)
 */

public class SelectableArticle {
    private Article article;
    private boolean selectable;

    public SelectableArticle(Article article){
        this(article, false);
    }

    public SelectableArticle(Article article, boolean selectable){
        this.article = article;
        this.selectable = selectable;
    }

    public String getImage(){
        if(getArticle().getMedia()!=null && getArticle().getMedia().size()>0)
            return getArticle().getMedia().get(0).getUri();
        return null;
    }

    public String getTitle(){
        return getArticle().getTitle();
    }

    public Article getArticle() {
        return article;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
}
