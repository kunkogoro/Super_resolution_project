package com.example.super_resolution_project.homePage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.super_resolution_project.R;
import com.example.super_resolution_project.been.OneItemHome;
import com.example.super_resolution_project.editImage.EditImageActivity;
import com.example.super_resolution_project.imageCompressor.ImageCompressorPage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomePageItemAdapter extends RecyclerView.Adapter<HomePageItemHodel> {

    private List<OneItemHome> list;

    public HomePageItemAdapter(List<OneItemHome> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public HomePageItemHodel onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_one_item_home,parent,false);

        HomePageItemHodel hodel = new HomePageItemHodel(view);

        return hodel;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HomePageItemHodel holder, int position) {

        OneItemHome itemHome = list.get(position);

        if (position == 0){
            holder.getLayout().setOnClickListener(v -> {

                v.getContext().startActivity(new Intent(v.getContext(), ImageCompressorPage.class));

            });
        }else if (position == 1){
            holder.getLayout().setOnClickListener(v -> {
                v.getContext().startActivity(new Intent(v.getContext(), EditImageActivity.class));
            });
        }

        holder.getImage().setAnimation(itemHome.getSourceImage());
        holder.getTitle().setText(itemHome.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
