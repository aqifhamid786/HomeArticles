package com.aqif.homearticles.articles;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.aqif.homearticles.R;
import com.aqif.homearticles.articles.reviewscreen.ReviewScreenFragment;
import com.aqif.homearticles.articles.selectionscreen.SelectionScreenFragment;
import com.aqif.homearticles.base.livedata.ClickLiveEvent;
import com.aqif.homearticles.databinding.ActivityArticlesBinding;

public class ArticlesActivity extends AppCompatActivity {

    private ActivityArticlesBinding dataBinding;
    private ArticlesActivityComponent diComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_articles);
        dataBinding.setLifecycleOwner(this);
        setSupportActionBar(dataBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        diComponent = DaggerArticlesActivityComponent.create();
        createSelectionScreen();
    }

    void createSelectionScreen(){
        SelectionScreenFragment fragment = diComponent.getSelectionScreenFragment();
        fragment.getPreviewLiveEvent().observe(fragment, (Observer<Object>) object -> createReviewScreen());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragments_container, fragment);
        transaction.commit();
    }

    void createReviewScreen(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("review_screen");
        transaction.add(R.id.fragments_container, diComponent.getReviewScreenFragment());
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
