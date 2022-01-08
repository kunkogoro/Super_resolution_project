package com.example.super_resolution_project.introductActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.super_resolution_project.R;
import com.example.super_resolution_project.homePage.HomePageActivity;
//import com.example.eat_fast.data.SourceSound;


public class IntroductoryActivity extends AppCompatActivity {
    private ImageView logo, splashImg;

    private TextView appName;
    private LottieAnimationView lottie;
    private SharedPreferences sharedPreferences;
    // Thời gian thoát activity
    private long lastTimePressBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Xóa status bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introductory_main);
        // Ánh xạ view
        getView();

        runAnimation();

        init();
    }

    @Override
    public void onBackPressed() {
        if (lastTimePressBack == 0 || System.currentTimeMillis() - lastTimePressBack > 2000) {
            lastTimePressBack = System.currentTimeMillis();
            Toast.makeText(IntroductoryActivity.this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
        } else {
            finish();

        }
    }

    private void init() {
        // Lấy dữ liệu chạy lần đầu
        sharedPreferences = getSharedPreferences(IntroductConfiguration.START_FIRST, MODE_PRIVATE);
        boolean isRuned = sharedPreferences.getBoolean("run", false);
        if (!isRuned) {
            // View pager
            ViewPager viewPager = findViewById(R.id.pager);
            ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), IntroductoryActivity.this);
            viewPager.setAdapter(adapter);
        } else {
            // Đã chạy rồi thì vô thẳng home page
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent();
                intent.setClass(IntroductoryActivity.this, HomePageActivity.class);
                finish();
                startActivity(intent);
            }, 4500);
        }
    }

    private void runAnimation() {
        runOnUiThread(() -> {
            splashImg.animate().translationY(-3000).setDuration(1500).setStartDelay(4500);
            logo.animate().translationY(3000).setDuration(1500).setStartDelay(4500);
            appName.animate().translationY(3000).setDuration(1500).setStartDelay(4500);
            lottie.animate().translationY(3000).setDuration(1000).setStartDelay(4500);
        });
    }

    private void getView() {
        logo = findViewById(R.id.logo);
        appName = findViewById(R.id.appName);
        splashImg = findViewById(R.id.img);
        lottie = findViewById(R.id.lottie);
    }

    // GETTER AND SETTER
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}