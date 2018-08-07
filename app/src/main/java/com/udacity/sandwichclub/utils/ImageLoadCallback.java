package com.udacity.sandwichclub.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;

public class ImageLoadCallback implements Callback {

    private ProgressBar progressBar;
    private ImageView imageView;
    private boolean isFromListView;

    public ImageLoadCallback(ProgressBar progressBar, ImageView imageView, boolean isFromListView) {
        this.progressBar = progressBar;
        this.imageView = imageView;
        this.isFromListView = isFromListView;
    }

    public ImageLoadCallback(ImageView imageView, boolean isFromListView) {
        this.imageView = imageView;
        this.isFromListView = isFromListView;
    }

    @Override
    public void onSuccess() {
        if (imageView != null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError() {
        if (imageView != null) {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (isFromListView) {
                imageView.setPadding(300,300,300,300);
            } else {
                imageView.setPadding(200,200,200,200);
            }
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
