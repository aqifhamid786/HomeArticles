package com.aqif.homearticles.articles.selectionscreen;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aqif.homearticles.articles.repository.IArticlesRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Reusable;

@Reusable
public class SelectionScreenViewModelFactory implements ViewModelProvider.Factory {

    private IArticlesRepository articlesRepository;

    @Inject
    public SelectionScreenViewModelFactory(IArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SelectionScreenViewModel.class)) {
            return (T) new SelectionScreenViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
