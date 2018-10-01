package com.aqif.homearticles.articles;

import com.aqif.homearticles.articles.reviewscreen.ReviewScreenFragment;
import com.aqif.homearticles.articles.selectionscreen.SelectionScreenFragment;
import com.aqif.homearticles.articles.sharedviewmodel.SharedViewModelFactory;

import dagger.Component;

@Component(modules = ArticlesActivityModule.class)
public interface ArticlesActivityComponent {
    SelectionScreenFragment getSelectionScreenFragment();
    ReviewScreenFragment getReviewScreenFragment();
}
