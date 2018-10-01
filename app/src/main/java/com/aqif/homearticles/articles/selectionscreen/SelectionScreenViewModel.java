package com.aqif.homearticles.articles.selectionscreen;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.aqif.homearticles.articles.repository.IArticlesRepository;
import com.aqif.homearticles.articles.repository.SelectableArticle;
import com.aqif.homearticles.base.BaseRepositoryResponse;
import com.aqif.homearticles.base.livedata.ClickLiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SelectionScreenViewModel extends ViewModel {

    public enum SCREEN_STATE{
        SELECTING,
        SELECTION_STOPPED_WAITING_FOR_MORE,
        SELECTION_STOPPED_NO_MORE_ARTICLES
    }

    private SCREEN_STATE screenState;

    public static final int DEFAULT_ARTICLES_COUNT = 10;

    private List<SelectableArticle> articles;
    public MutableLiveData<String> statusMessage;

    public MutableLiveData<String> articleImage;
    public MutableLiveData<Integer> isBackEnabled;
    public MutableLiveData<Integer> isLikeDislikeEnabled;
    public MutableLiveData<Integer> isReviewEnabled;
    public MutableLiveData<Integer> isMessageEnabled;
    public MutableLiveData<Integer> haveMoreArticles;
    public MutableLiveData<String> likeStatsNumber;

    private ClickLiveEvent<Object> previewLiveEvent;
    private IArticlesRepository articlesRepository;

    private int numberOfArticles = DEFAULT_ARTICLES_COUNT;
    private int currentArticleNumber = 0;
    private int likedArticlesCount = 0;


    private final Observer<BaseRepositoryResponse<List<SelectableArticle>>> articlesRepositoryObserver = articlesRepositoryResponse -> {
        statusMessage.setValue(articlesRepositoryResponse.getMessage());
        articles = articlesRepositoryResponse.getData();
        likeStatsNumber.setValue(String.format("%d/%d",likedArticlesCount, articles.size()));

        updateState();
        updateViews();
    };

    public SelectionScreenViewModel(){

        this.statusMessage = new MutableLiveData<>();
        this.articles = new ArrayList<>();
        this.articleImage = new MutableLiveData<>();
        this.isBackEnabled = new MutableLiveData<>();
        this.isBackEnabled.setValue(View.INVISIBLE);
        this.isLikeDislikeEnabled = new MutableLiveData<>();
        this.isLikeDislikeEnabled.setValue(View.VISIBLE);
        this.isReviewEnabled = new MutableLiveData<>();
        this.isReviewEnabled.setValue(View.INVISIBLE);
        this.isMessageEnabled = new MutableLiveData<>();
        this.isMessageEnabled.setValue(View.INVISIBLE);
        this.haveMoreArticles = new MutableLiveData<>();
        this.haveMoreArticles.setValue(View.VISIBLE);
        this.likeStatsNumber = new MutableLiveData<>();
        this.likeStatsNumber.setValue(String.format("%d/%d",likedArticlesCount, articles.size()));
        this.previewLiveEvent = new ClickLiveEvent<>();
        updateState();
    }

    public void setArticlesRepository(IArticlesRepository articlesRepository){
        this.articlesRepository = articlesRepository;
        this.articlesRepository.getArticles().observeForever(articlesRepositoryObserver);
        this.articlesRepository.loadArticles(numberOfArticles);
    }

    public ClickLiveEvent<Object> getPreviewLiveEvent() {
        return previewLiveEvent;
    }

    public void onNumberOfArticlesChanged(Object numberOfArticlesObject){
        this.numberOfArticles = Integer.parseInt(numberOfArticlesObject.toString());
        this.articlesRepository.loadArticles(numberOfArticles);
    }

    public void onArticleLiked(){
        SelectableArticle article = articles.get(currentArticleNumber);
        if(!article.isSelectable()){
            likedArticlesCount += 1;
            this.likeStatsNumber.setValue(String.format("%d/%d",likedArticlesCount, articles.size()));
            article.setSelectable(true);
        }
        currentArticleNumber += 1;
        updateState();
        updateViews();
    }

    public void onArticleDisliked(){

        SelectableArticle article = articles.get(currentArticleNumber);
        if(article.isSelectable()){
            likedArticlesCount -= 1;
            this.likeStatsNumber.setValue(String.format("%d/%d",likedArticlesCount, articles.size()));
            article.setSelectable(false);
        }

        articles.get(currentArticleNumber).setSelectable(false);
        currentArticleNumber += 1;
        updateState();
        updateViews();
    }

    public void onBackPressed(){
        currentArticleNumber -= 1;
        updateState();
        updateViews();
    }

    public void onReviewPressed(){
        previewLiveEvent.setValue(null);
    }

    private void updateState(){

        boolean isLastArticleReviewed = currentArticleNumber >= articles.size();
        boolean isWaitingForMore = numberOfArticles > articles.size();

        if(!isLastArticleReviewed){
            screenState = SCREEN_STATE.SELECTING;
        } else if (!isWaitingForMore && isLastArticleReviewed){
            screenState = SCREEN_STATE.SELECTION_STOPPED_NO_MORE_ARTICLES;
        } else if(isWaitingForMore && isLastArticleReviewed){
            screenState = SCREEN_STATE.SELECTION_STOPPED_WAITING_FOR_MORE;
        }
    }

    private void updateViews(){

        if(currentArticleNumber < articles.size()){
            articleImage.setValue(articles.get(currentArticleNumber).getImage());
        } else if(currentArticleNumber > articles.size()){
            currentArticleNumber = articles.size();
        }

        this.isBackEnabled.setValue(currentArticleNumber == 0 ? View.INVISIBLE : View.VISIBLE);

        switch (screenState){
            case SELECTING:
                isLikeDislikeEnabled.setValue(View.VISIBLE);
                isReviewEnabled.setValue(View.INVISIBLE);
                haveMoreArticles.setValue(View.VISIBLE);
                isMessageEnabled.setValue(View.INVISIBLE);
                break;

            case SELECTION_STOPPED_NO_MORE_ARTICLES:
                isLikeDislikeEnabled.setValue(View.INVISIBLE);
                isReviewEnabled.setValue(View.VISIBLE);
                haveMoreArticles.setValue(View.INVISIBLE);
                isMessageEnabled.setValue(View.INVISIBLE);
                break;

            case SELECTION_STOPPED_WAITING_FOR_MORE:
                isLikeDislikeEnabled.setValue(View.INVISIBLE);
                isReviewEnabled.setValue(View.INVISIBLE);
                haveMoreArticles.setValue(View.INVISIBLE);
                isMessageEnabled.setValue(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onCleared() {
        this.articlesRepository.getArticles().removeObserver(articlesRepositoryObserver);
        this.articlesRepository.clearRequests();
        this.articlesRepository = null;
        super.onCleared();
    }
}
