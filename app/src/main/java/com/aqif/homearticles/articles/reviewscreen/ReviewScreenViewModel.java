package com.aqif.homearticles.articles.reviewscreen;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;

import com.aqif.homearticles.articles.repository.IArticlesRepository;
import com.aqif.homearticles.articles.repository.SelectableArticle;
import com.aqif.homearticles.base.BaseRepositoryResponse;
import com.aqif.homearticles.base.livedata.ClickLiveEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ReviewScreenViewModel extends ViewModel {

    public static final int LINEAR_LAYOUT_ID = 0;
    public static final int GRID_LAYOUT_ID = 1;

    private MutableLiveData<List<ArticleViewModel>> articleViewModelsLiveData;
    private ClickLiveEvent<Integer> layoutRadioButtonsLiveEvent;

    private IArticlesRepository articlesRepository;
    private int defaultLayoutEventId;

    private final Observer<BaseRepositoryResponse<List<SelectableArticle>>> articlesRepositoryObserver = articlesRepositoryResponse -> {
        ArrayList<ArticleViewModel> articleViewModels = new ArrayList<>();
        Observable
                .fromIterable(articlesRepositoryResponse.getData())
                .doOnComplete(() -> articleViewModelsLiveData.setValue(articleViewModels))
                .subscribe(selectableArticle -> articleViewModels.add(new ArticleViewModel(selectableArticle)));
    };

    public ReviewScreenViewModel(){
        articleViewModelsLiveData = new MutableLiveData<>();
        layoutRadioButtonsLiveEvent = new ClickLiveEvent<>();
        defaultLayoutEventId = LINEAR_LAYOUT_ID;
    }

    public boolean isGridDefaultLayout(){
        return defaultLayoutEventId == GRID_LAYOUT_ID;
    }

    public boolean isListDefaultLayout(){
        return defaultLayoutEventId == LINEAR_LAYOUT_ID;
    }

    public void onRadioButtonSelected(int radioButtonId){
        layoutRadioButtonsLiveEvent.setValue(radioButtonId);
    }

    public LiveData<List<ArticleViewModel>> getArticleViewModelsLiveData() {
        return articleViewModelsLiveData;
    }

    public ClickLiveEvent<Integer> getLayoutRadioButtonsLiveEvent() {
        return layoutRadioButtonsLiveEvent;
    }

    public void setArticlesRepository(IArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
        this.articlesRepository.getArticles().observeForever(articlesRepositoryObserver);
    }

}
