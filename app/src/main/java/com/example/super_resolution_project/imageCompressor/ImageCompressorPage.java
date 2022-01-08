package com.example.super_resolution_project.imageCompressor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.super_resolution_project.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import id.zelory.compressor.Compressor;


public class ImageCompressorPage extends AppCompatActivity {

    private ImageView imageStart,imageEnd;
    private TextView size_image_start,size_image_end;
    private CardView cardView1,cardView2,cardView3,cardView4,cardView5,card_size_start,card_size_end;
    private File originalImage,compressorImage;
    private String filePath;
    private File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compressor_page);
        getView();
        eventLoadImame();
        eventCompressor();


    }
    public void eventCompressor(){

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

//        Python py = Python.getInstance();
//        PyObject object = py.getModule("test");

        cardView2.setOnClickListener(v -> {

//            PyObject pyObject = object.callAttr("compressor",path.toString());
//            System.out.println(path.toString());
//            Toast.makeText(this, pyObject.toString(), Toast.LENGTH_SHORT).show();
            try {
                compressorImage = new Compressor(ImageCompressorPage.this).
                        setQuality(70).setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(filePath).compressToFile(originalImage);
                File finalFile = new File(filePath, originalImage.getName());
                Bitmap finalBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                cardView5.setVisibility(View.VISIBLE);
                imageEnd.setImageBitmap(finalBitmap);
                size_image_end.setText("Size:" + Formatter.formatShortFileSize(ImageCompressorPage.this,finalFile.length()));
                card_size_end.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });


    }
    public void eventLoadImame(){
        cardView1.setOnClickListener(v -> {
            requestPermissions();
        });

    }

    public void requestPermissions(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(ImageCompressorPage.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                openImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ImageCompressorPage.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }
    public void openImage(){
       TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener(){

           @Override
           public void onImageSelected(Uri uri) {
               try {
//                   Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                   final InputStream imageStream = getContentResolver().openInputStream(uri);
                   final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                   imageStart.setImageBitmap(bitmap);
                   originalImage = new File(uri.getPath().replace("raw/",""));
                   size_image_start.setText("Size: " + Formatter.formatShortFileSize(ImageCompressorPage.this, originalImage.length()));
                   card_size_start.setVisibility(View.VISIBLE);
               }catch (Exception e){
                   Toast.makeText(ImageCompressorPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       };

        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(ImageCompressorPage.this)
                .setOnImageSelectedListener(listener).create();
        tedBottomPicker.show(getSupportFragmentManager());
    }
    void getView(){
        imageStart = findViewById(R.id.image_1);
        imageEnd = findViewById(R.id.image_2);
        cardView1 = findViewById(R.id.cardView2);
        cardView2 = findViewById(R.id.cardView3);
        cardView3 = findViewById(R.id.cardView4);
        cardView4 = findViewById(R.id.cardView7);
        cardView5 = findViewById(R.id.cardView5);
        size_image_start = findViewById(R.id.size_info_start);
        card_size_start = findViewById(R.id.cart_size_start);
        card_size_end = findViewById(R.id.cart_size_end);
        size_image_end = findViewById(R.id.size_info_end);

        path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+ "/imageCompressor");
        System.out.println("Đường dẫn " + path.getAbsolutePath());
        filePath = path.getAbsolutePath();
        if (!path.exists()){
            System.out.println("Tạo đường dẫn " + path.mkdirs());
        }
    }

}