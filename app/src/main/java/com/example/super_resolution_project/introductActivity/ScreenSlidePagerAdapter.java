package com.example.super_resolution_project.introductActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.super_resolution_project.R;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private final com.example.super_resolution_project.introductActivity.IntroductoryActivity appCompatActivity;

    public ScreenSlidePagerAdapter(@NonNull FragmentManager fm, com.example.super_resolution_project.introductActivity.IntroductoryActivity appCompatActivity) {
        super(fm);
        this.appCompatActivity = appCompatActivity;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        IntroductFragmentObject introductFragmentObject;
        switch (position) {
            case 0:
                introductFragmentObject = new IntroductFragmentObject(R.layout.activity_introductory_page_1, appCompatActivity);
                break;
            case 1:
                introductFragmentObject = new IntroductFragmentObject(R.layout.activity_introductory_page_2, appCompatActivity);
                break;
            default:
                introductFragmentObject = new IntroductFragmentObject(R.layout.activity_introductory_page_3, com.example.super_resolution_project.introductActivity.IntroductConfiguration.END, appCompatActivity);
                break;
        }
        return introductFragmentObject;
    }

    @Override
    public int getCount() {
        return com.example.super_resolution_project.introductActivity.IntroductConfiguration.MAX_PAGE;
    }

}