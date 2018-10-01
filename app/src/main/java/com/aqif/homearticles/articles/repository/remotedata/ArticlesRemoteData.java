package com.aqif.homearticles.articles.repository.remotedata;

import com.aqif.homearticles.articles.repository.remotedata.response.ArticlesResponse;

import javax.inject.Inject;

import dagger.Reusable;
import io.reactivex.Observable;

@Reusable
public class ArticlesRemoteData {
    IArticlesAPI articlesAPI;

    @Inject
    public ArticlesRemoteData(IArticlesAPI articlesAPI){
        this.articlesAPI = articlesAPI;
    }

    public Observable<ArticlesResponse> getArticles(int offset, int limit){
        return articlesAPI.getArticles(offset, limit);
    }

}
