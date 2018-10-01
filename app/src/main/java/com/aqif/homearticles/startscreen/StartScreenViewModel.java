package com.aqif.homearticles.startscreen;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.view.View;

import com.aqif.homearticles.articles.ArticlesActivity;
import com.aqif.homearticles.base.livedata.ClickLiveEvent;


public class StartScreenViewModel extends ViewModel {

    private ClickLiveEvent<Object> startClickLiveEvent;

    public StartScreenViewModel(){
        startClickLiveEvent = new ClickLiveEvent<>();
    }

    public ClickLiveEvent<Object> getStartClickLiveEvent() {
        return startClickLiveEvent;
    }

    public void onStartClicked(){
        startClickLiveEvent.setValue(null);

    }
}
