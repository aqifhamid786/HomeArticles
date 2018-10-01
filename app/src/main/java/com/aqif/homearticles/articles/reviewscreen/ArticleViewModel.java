package com.aqif.homearticles.articles.reviewscreen;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.aqif.homearticles.R;
import com.aqif.homearticles.articles.repository.SelectableArticle;

/**
 * ViewModel for each article list item in review screen.
 */

public class ArticleViewModel extends ViewModel {

    public MutableLiveData<String> title;
    public MutableLiveData<Integer> titleVisibility;
    public MutableLiveData<String> imageUrl;
    public MutableLiveData<Integer> imageSelected;

    public ArticleViewModel(SelectableArticle article){
        title = new MutableLiveData<>();
        title.setValue(article.getTitle());
        imageUrl  = new MutableLiveData<>();
        imageUrl.setValue(article.getImage());
        titleVisibility = new MutableLiveData<>();
        imageSelected = new MutableLiveData<>();
        imageSelected.setValue(article.isSelectable()?View.VISIBLE:View.INVISIBLE);

    }

    public void setShowTitle(boolean showTitle){
        this.titleVisibility.setValue(showTitle ? View.VISIBLE : View.GONE);
    }

    public int getLayoutId(){
        return R.layout.article_list_item;
    }

}
