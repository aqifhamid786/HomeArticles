package com.aqif.homearticles.articles.repository;

import android.arch.lifecycle.LiveData;

import com.aqif.homearticles.base.BaseRepositoryResponse;

import java.util.List;

public interface IArticlesRepository {
    LiveData<BaseRepositoryResponse<List<SelectableArticle>>> getArticles();
    void loadArticles(int numberOfArticles);
    void clearRequests();
}
