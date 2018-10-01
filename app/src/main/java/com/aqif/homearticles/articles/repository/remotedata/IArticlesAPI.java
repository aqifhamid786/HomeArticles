package com.aqif.homearticles.articles.repository.remotedata;

import com.aqif.homearticles.articles.repository.remotedata.response.ArticlesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IArticlesAPI {
    @GET("articles?appDomain=1&locale=de_DE")
    Observable<ArticlesResponse> getArticles(@Query("offset") int appDomain, @Query("limit") int limit);
}
