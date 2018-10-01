package com.aqif.homearticles.articles.selectionscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.aqif.homearticles.R;
import com.aqif.homearticles.articles.DaggerArticlesActivityComponent;
import com.aqif.homearticles.articles.sharedviewmodel.DaggerSharedViewModelComponent;
import com.aqif.homearticles.articles.sharedviewmodel.SharedViewModel;
import com.aqif.homearticles.articles.sharedviewmodel.SharedViewModelFactory;
import com.aqif.homearticles.base.livedata.ClickLiveEvent;
import com.aqif.homearticles.databinding.FragmentSelectionScreenBinding;

public class SelectionScreenFragment extends Fragment{

    private SelectionScreenViewModel viewModel;
    private ClickLiveEvent<Object> previewLiveEvent;
    private ArrayAdapter<CharSequence> adapter;

    public SelectionScreenFragment(){
        previewLiveEvent = new ClickLiveEvent<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        //      Fetch shared VM
        SharedViewModelFactory sharedViewModelFactory = DaggerSharedViewModelComponent.create().getSharedViewModelFactory();
        SharedViewModel sharedViewModel = ViewModelProviders.of(getActivity(), sharedViewModelFactory).get(SharedViewModel.class);

//      create fragment view model
        viewModel = ViewModelProviders.of(this).get(SelectionScreenViewModel.class);
        viewModel.setArticlesRepository(sharedViewModel.getArticlesRepository());

//      register with view model.
        viewModel.getPreviewLiveEvent().observe(this, (Observer<Object>) object -> {
            previewLiveEvent.setValue(object);
        });

        // simple adapter to load array items in spinner.
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.article_count_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//      create view binding
        FragmentSelectionScreenBinding dataBinding = FragmentSelectionScreenBinding.inflate(inflater, container, false);
        dataBinding.setLifecycleOwner(this);
        dataBinding.setViewModel(viewModel);

        dataBinding.countSpinner.setAdapter(adapter);

        return dataBinding.getRoot();
    }

    public ClickLiveEvent<Object> getPreviewLiveEvent() {
        return previewLiveEvent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
