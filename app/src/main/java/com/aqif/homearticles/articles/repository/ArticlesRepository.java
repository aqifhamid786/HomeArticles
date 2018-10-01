package com.aqif.homearticles.articles.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.aqif.homearticles.articles.repository.remotedata.ArticlesRemoteData;
import com.aqif.homearticles.base.BaseRepositoryResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Reusable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Caches and supplies articles data to other modules.
 */

@Reusable
public class ArticlesRepository implements IArticlesRepository {

    private final int MAX_NO_OF_ARTICLES_TO_FETCH = 100;

    // TODO: persists data to db to provide offline experience.
    private final ArticlesRemoteData articlesRemoteData;
    private MutableLiveData<BaseRepositoryResponse<List<SelectableArticle>>> articlesListResponse;

    private int noOfArticlesToLoad;
    private ArrayList<SelectableArticle> cachedArticles;

    private Disposable currentRequestDisposable;
    private Disposable retryDisposable;

    private int retryWaitTimeSec = 10;

    @Inject
    public ArticlesRepository(ArticlesRemoteData articlesRemoteData){
        this.articlesRemoteData = articlesRemoteData;
        this.articlesListResponse = new MutableLiveData<>();
        this.noOfArticlesToLoad = 0;
        this.cachedArticles = new ArrayList<>();
    }

    @Override
    public LiveData<BaseRepositoryResponse<List<SelectableArticle>>> getArticles() {
        return articlesListResponse;
    }

    @Override
    public void loadArticles(int numberOfArticles){

//        Log.d("requests",numberOfArticles+", "+cachedArticles.size());

        this.noOfArticlesToLoad = numberOfArticles;
        if(noOfArticlesToLoad <= cachedArticles.size()) {
            clearRequests();
            ArrayList<SelectableArticle> articles = new ArrayList<>();
            Observable.fromIterable(cachedArticles).take(numberOfArticles).subscribe(article -> articles.add(article));
            articlesListResponse.setValue(new BaseRepositoryResponse<>(null, articles));
        }else if(currentRequestDisposable==null){
            tryLoadingArticles();
        }
    }

    private void tryLoadingArticles(){
        if(noOfArticlesToLoad > cachedArticles.size()) {
            int amountToLoad = noOfArticlesToLoad - cachedArticles.size();
            if(amountToLoad > MAX_NO_OF_ARTICLES_TO_FETCH)
                amountToLoad = MAX_NO_OF_ARTICLES_TO_FETCH;
            getArticlesFromNetwork(cachedArticles.size(), amountToLoad);
        }
    }

    private void getArticlesFromNetwork(int offset, int amountToLoad){
        articlesListResponse.setValue(new BaseRepositoryResponse<>("Loading articles...", cachedArticles));
        currentRequestDisposable = articlesRemoteData.getArticles(offset, amountToLoad)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response ->response.getEmbedded().getArticles())
                .doOnComplete(()-> {
                    currentRequestDisposable = null;
                    tryLoadingArticles();
                })
                .subscribe(
                        articles-> {
                            Observable.fromIterable(articles).subscribe(article -> cachedArticles.add(new SelectableArticle(article)));
                            articlesListResponse.setValue(new BaseRepositoryResponse<>(null, cachedArticles));
                        },
                        error-> retryPeriodically(offset, amountToLoad)
                );
    }

    private void retryPeriodically(int offset, int amountToLoad){
        retryDisposable = Observable.intervalRange(1, retryWaitTimeSec, 1, 1, TimeUnit.SECONDS)
                .timeInterval()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() ->  {
                    getArticlesFromNetwork(offset, amountToLoad);
                })
                .subscribe(interval ->  articlesListResponse.setValue(new BaseRepositoryResponse<>(String.format("Retrying in %d seconds...",
                        (retryWaitTimeSec-interval.value())), cachedArticles)));
    }

    @Override
    public void clearRequests(){
        if(currentRequestDisposable !=null && !currentRequestDisposable.isDisposed())
            currentRequestDisposable.dispose();
        if(retryDisposable !=null && !retryDisposable.isDisposed())
            retryDisposable.dispose();

    }
}
