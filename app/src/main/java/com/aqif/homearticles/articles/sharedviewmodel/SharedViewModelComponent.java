package com.aqif.homearticles.articles.sharedviewmodel;

import dagger.Component;

@Component(modules = SharedViewModelModule.class)
public interface SharedViewModelComponent {
    SharedViewModelFactory getSharedViewModelFactory();
}
