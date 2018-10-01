package com.aqif.homearticles.articles;

import com.aqif.homearticles.articles.reviewscreen.ReviewScreenFragment;
import com.aqif.homearticles.articles.selectionscreen.SelectionScreenFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class ArticlesActivityModule {

    @Provides
    public ReviewScreenFragment provideReviewScreenFragment(){
        return new ReviewScreenFragment();
    }

    @Provides
    public SelectionScreenFragment provideSelectionScreenFragment(){
        return new SelectionScreenFragment();
    }

}
