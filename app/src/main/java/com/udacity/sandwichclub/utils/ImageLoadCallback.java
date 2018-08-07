package com.udacity.sandwichclub.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;

public class ImageLoadCallback implements Callback {

    private ProgressBar progressBar;
    private ImageView imageView;
    private boolean isFromListView;

    //constructor for custom Callback object
    public ImageLoadCallback(ProgressBar progressBar, ImageView imageView, boolean isFromListView) {
        this.progressBar = progressBar;
        this.imageView = imageView;
        this.isFromListView = isFromListView;
    }

    /**
     * on successful loading of image, set the scaleType to CENTER_CROP,
     * remove any padding added in case of image load error,
     * hide the progress bar
     */
    @Override
    public void onSuccess() {
        if (imageView != null) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    /**
     * in case of image loading error, set the scaleType to FIT_CENTER,
     * add appropriate padding for displaying error image based on listview/gridview,
     * hide the progress bar
     */
    @Override
    public void onError() {
        if (imageView != null) {
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (isFromListView) {
                imageView.setPadding(300, 300, 300, 300);
            } else {
                imageView.setPadding(200, 200, 200, 200);
            }
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
