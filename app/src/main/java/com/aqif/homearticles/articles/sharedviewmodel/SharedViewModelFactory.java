package com.aqif.homearticles.articles.sharedviewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aqif.homearticles.articles.repository.IArticlesRepository;

public class SharedViewModelFactory implements ViewModelProvider.Factory {

    private IArticlesRepository articlesRepository;

    public SharedViewModelFactory(IArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(articlesRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
