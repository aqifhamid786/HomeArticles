package com.aqif.homearticles.articles.sharedviewmodel;

import android.arch.lifecycle.ViewModel;

import com.aqif.homearticles.articles.repository.IArticlesRepository;

public class SharedViewModel extends ViewModel {

    private IArticlesRepository articlesRepository;

    public SharedViewModel(IArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
    }

    public IArticlesRepository getArticlesRepository() {
        return articlesRepository;
    }
}
