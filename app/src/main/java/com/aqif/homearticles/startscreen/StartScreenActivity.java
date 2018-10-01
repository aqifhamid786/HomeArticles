package com.aqif.homearticles.startscreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aqif.homearticles.R;
import com.aqif.homearticles.articles.ArticlesActivity;
import com.aqif.homearticles.databinding.ActivityStartScreenBinding;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStartScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_start_screen);
        StartScreenViewModel viewModel = ViewModelProviders.of(this).get(StartScreenViewModel.class);

        // registering for start button live event.
        viewModel.getStartClickLiveEvent().observe(this, (Observer<Object>) object -> {
            startArticlesActivit();
        });
        binding.setViewModel(viewModel);
    }

    private void startArticlesActivit() {
        Intent intent = new Intent(this, ArticlesActivity.class);
        startActivity(intent);
    }

}
