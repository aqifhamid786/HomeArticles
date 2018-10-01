package com.aqif.homearticles.articles.sharedviewmodel;

import com.aqif.homearticles.articles.repository.ArticlesRepositoryModule;
import com.aqif.homearticles.articles.repository.IArticlesRepository;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

@Module(includes = ArticlesRepositoryModule.class)
public class SharedViewModelModule {

    @Reusable
    @Provides
    public SharedViewModelFactory provideSharedViewModel(IArticlesRepository repository){
        return new SharedViewModelFactory(repository);
    }
}
