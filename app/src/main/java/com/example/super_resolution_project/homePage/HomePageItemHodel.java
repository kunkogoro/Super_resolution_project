package com.example.super_resolution_project.homePage;

import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.super_resolution_project.R;

import org.jetbrains.annotations.NotNull;

public class HomePageItemHodel extends RecyclerView.ViewHolder {

    private TextView title;
    private LottieAnimationView image;
    private CardView layout;

    public HomePageItemHodel(@NonNull @NotNull View itemView) {
        super(itemView);
        getView(itemView);
    }
    public void getView(View view){

        image = view.findViewById(R.id.one_image);
        title = view.findViewById(R.id.one_title);
        layout = view.findViewById(R.id.cvid);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public LottieAnimationView getImage() {
        return image;
    }

    public void setImage(LottieAnimationView image) {
        this.image = image;
    }

    public CardView getLayout() {
        return layout;
    }

    public void setLayout(CardView layout) {
        this.layout = layout;
    }
}
