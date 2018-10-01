package com.aqif.homearticles.articles.reviewscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aqif.homearticles.R;
import com.aqif.homearticles.articles.sharedviewmodel.DaggerSharedViewModelComponent;
import com.aqif.homearticles.articles.sharedviewmodel.SharedViewModel;
import com.aqif.homearticles.articles.sharedviewmodel.SharedViewModelFactory;
import com.aqif.homearticles.databinding.FragmentReviewScreenBinding;

public class ReviewScreenFragment extends Fragment {

    private ReviewScreenViewModel viewModel;
    private ReviewArticleRecyclerAdapter articlesAdapter;
    private FragmentReviewScreenBinding dataBinding;
    private int recyclerLayoutManagerId;

    public void setViewModel(ReviewScreenViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

//      Fetch shared VM
        SharedViewModelFactory sharedViewModelFactory = DaggerSharedViewModelComponent.create().getSharedViewModelFactory();
        SharedViewModel sharedViewModel = ViewModelProviders.of(getActivity(), sharedViewModelFactory).get(SharedViewModel.class);

//      create fragment view model
        viewModel = ViewModelProviders.of(this).get(ReviewScreenViewModel.class);
        viewModel.setArticlesRepository(sharedViewModel.getArticlesRepository());

//      setup adapter and view model events.
        articlesAdapter = new ReviewArticleRecyclerAdapter();
        viewModel.getArticleViewModelsLiveData().observe(this, articleViewModels -> {
            articlesAdapter.setArticleViewModels(articleViewModels);
        });

        // setup toggle live event
        recyclerLayoutManagerId = ReviewScreenViewModel.LINEAR_LAYOUT_ID;
        viewModel.getLayoutRadioButtonsLiveEvent().observe(this, (Observer<Integer>) toggleLayoutRadioButtonId->{
            if(toggleLayoutRadioButtonId == R.id.grid_radio)
                recyclerLayoutManagerId = ReviewScreenViewModel.GRID_LAYOUT_ID;
            else
                recyclerLayoutManagerId = ReviewScreenViewModel.LINEAR_LAYOUT_ID;
            updateLayoutManager();
        });

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//      create view binding
        dataBinding = FragmentReviewScreenBinding.inflate(inflater, container, false);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setViewModel(viewModel);

        updateLayoutManager();
        dataBinding.articlesRecyclerView.setAdapter(articlesAdapter);

        return dataBinding.getRoot();
    }

    private void updateLayoutManager(){
        RecyclerView.LayoutManager layoutManager;
        if(recyclerLayoutManagerId == ReviewScreenViewModel.GRID_LAYOUT_ID) {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }

        articlesAdapter.isListArrangement(recyclerLayoutManagerId == ReviewScreenViewModel.LINEAR_LAYOUT_ID);
        if(dataBinding!=null)
            dataBinding.articlesRecyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void onDestroyView() {
        dataBinding.articlesRecyclerView.setAdapter(null);
        dataBinding = null;
        super.onDestroyView();
    }
}
