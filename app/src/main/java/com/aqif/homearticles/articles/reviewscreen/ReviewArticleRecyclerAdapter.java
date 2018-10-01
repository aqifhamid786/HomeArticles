package com.aqif.homearticles.articles.reviewscreen;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aqif.homearticles.BR;
import com.aqif.homearticles.articles.reviewscreen.ReviewArticleRecyclerAdapter.ArticleViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ReviewArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleViewHolder>
{

    private List<ArticleViewModel> articleViewModels;

    public ReviewArticleRecyclerAdapter() {
        articleViewModels = new ArrayList<>();
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, viewType, viewGroup, false);
        return new ArticleViewHolder(viewDataBinding);
    }

    @Override
    public int getItemViewType(int position) {
        return articleViewModels.get(position).getLayoutId();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder baseViewHolder, int position) {
        baseViewHolder.getDataBinding().setVariable(BR.articleViewModel, articleViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return articleViewModels.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setArticleViewModels(List<ArticleViewModel> articleViewModels){
        this.articleViewModels = articleViewModels;
        notifyDataSetChanged();
    }

    public void isListArrangement(boolean isListFormat){
        Observable.fromIterable(articleViewModels).subscribe(articleViewModel -> articleViewModel.setShowTitle(isListFormat));
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding dataBinding;

        public ArticleViewHolder(@NonNull ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.dataBinding = dataBinding;
            this.dataBinding.executePendingBindings();
        }

        public ViewDataBinding getDataBinding() {
            return dataBinding;
        }

    }

}