package com.example.super_resolution_project.homePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ScrollView;

import com.example.super_resolution_project.R;
import com.example.super_resolution_project.been.OneItemHome;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private RecyclerView recyclerView;
    private List<OneItemHome> list;
    private HomePageItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getView();
        init();
        getData();
    }
    public void getData(){
        list.add(new OneItemHome(R.raw.camera_1,"Chỉnh sửa ảnh"));
        list.add(new OneItemHome(R.raw.camera_2,"Nén ảnh"));
        list.add(new OneItemHome(R.raw.camara_3,"Cải thiện ảnh"));
        list.add(new OneItemHome(R.raw.camera_4,"Hướng dẫn"));
    }
    public void init(){
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new HomePageItemAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void getView(){
        scrollView = findViewById(R.id.scrollView2);
        recyclerView = findViewById(R.id.list_item_home);
        list = new ArrayList<>();

    }
}