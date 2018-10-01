package com.aqif.homearticles.articles.repository;

import com.aqif.homearticles.articles.repository.remotedata.IArticlesAPI;
import com.aqif.homearticles.base.dagger.NetworkModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import retrofit2.Retrofit;

@Module(includes = NetworkModule.class)
public class ArticlesRepositoryModule {

    @Reusable
    @Provides
    public IArticlesAPI provideIArticlesAPI(Retrofit retrofit) {
        return retrofit.create(IArticlesAPI.class);
    }

    @Reusable
    @Provides
    public IArticlesRepository provideArticlesRepository(ArticlesRepository articlesRepository) {
        return articlesRepository;
    }


}
