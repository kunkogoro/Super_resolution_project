package com.example.super_resolution_project.been;

import android.view.View;
import android.widget.ImageView;

public class OneItemHome {

    private int sourceImage;
    private String title;

    public OneItemHome() {
    }

    public OneItemHome(int sourceImage, String title) {
        this.sourceImage = sourceImage;
        this.title = title;
    }

    public int getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(int sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
