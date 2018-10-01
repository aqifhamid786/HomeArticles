package com.aqif.homearticles.base;

import android.databinding.BindingAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.aqif.homearticles.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 *
 * Native views binding adapters to facilitate basic customized functionalities.
 *
 */

public class BaseBindingAdapters {

    @BindingAdapter("visibility")
    public static void setVisibility(View view, Integer visibility){
        if(visibility!=null)
            view.setVisibility(visibility);
    }

    @BindingAdapter("url")
    public static void setSrc(AppCompatImageView imageView, String url){
        if(url!=null){
            Glide.with(imageView).load(url).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
        }else{
            Glide.with(imageView).load(R.drawable.placeholder).transition(DrawableTransitionOptions.withCrossFade()).into(imageView);
        }
    }

}
