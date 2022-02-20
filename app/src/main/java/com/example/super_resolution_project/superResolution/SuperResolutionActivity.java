package com.example.super_resolution_project.superResolution;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.super_resolution_project.R;
import com.example.super_resolution_project.imageCompressor.ImageCompressorPage;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import id.zelory.compressor.Compressor;

public class SuperResolutionActivity extends AppCompatActivity {

    private ImageView imageStart,imageEnd;
    private TextView size_image_start,size_image_end;
    private CardView cardView1,cardView2,cardView3,cardView4,cardView5,card_size_start,card_size_end;
    private File originalImage,compressorImage;
    private String filePath;
    private File path;
    private BitmapDrawable bitmapDrawable;
    private Bitmap bitmap;
    private String imageString = "";
    private String URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_resolution);
        setContentView(R.layout.activity_image_compressor_page);
        getView();
        eventLoadImame();
       // eventCompressor();
        eventResolution();
    }
    void eventResolution(){

        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(this));
        }

        cardView2.setOnClickListener(v -> {
            System.out.println("Bắt đầu");
            bitmapDrawable = (BitmapDrawable) imageStart.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
            imageString = getStringImage(bitmap);

            final Python py = Python.getInstance();

            PyObject pyObject = py.getModule("ESGAN");
            System.out.println("Đang làm");
            System.out.println("Sup path: " + URI);
            PyObject pyObject1 = pyObject.callAttr("processing",imageString);


//            String deString = pyObject1.toString();
//            byte data[] = Base64.decode(deString,Base64.DEFAULT);
//            Bitmap bitmap1 = BitmapFactory.decodeByteArray(data,0,data.length);
//            imageEnd.setImageBitmap(bitmap1);
            String deString = pyObject1.toString();
            byte data[] = Base64.decode(deString,Base64.DEFAULT);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(data,0,data.length);
            cardView5.setVisibility(View.VISIBLE);
            imageEnd.setImageBitmap(bitmap1);

            try {
                compressorImage = new Compressor(SuperResolutionActivity.this).
                        setQuality(100).setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .setDestinationDirectoryPath(filePath).compressToFile(originalImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            File finalFile = new File(filePath, originalImage.getName());
            size_image_end.setText("Size: " + Formatter.formatShortFileSize(SuperResolutionActivity.this, finalFile.length()));
            card_size_end.setVisibility(View.VISIBLE);
            System.out.println("Kết thúc");
        });


    }
    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String encodeImage = Base64.encodeToString(bytes,Base64.DEFAULT);
        return encodeImage;
    }
//    public void eventCompressor(){
//
//        if(!Python.isStarted()){
//            Python.start(new AndroidPlatform(this));
//        }
//
////        Python py = Python.getInstance();
////        PyObject object = py.getModule("test");
//
//        cardView2.setOnClickListener(v -> {
//
////            PyObject pyObject = object.callAttr("compressor",path.toString());
////            System.out.println(path.toString());
////            Toast.makeText(this, pyObject.toString(), Toast.LENGTH_SHORT).show();
//            try {
//                compressorImage = new Compressor(SuperResolutionActivity.this).
//                        setQuality(70).setCompressFormat(Bitmap.CompressFormat.JPEG)
//                        .setDestinationDirectoryPath(filePath).compressToFile(originalImage);
//                File finalFile = new File(filePath, originalImage.getName());
//                Bitmap finalBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
//                cardView5.setVisibility(View.VISIBLE);
//                imageEnd.setImageBitmap(finalBitmap);
//                size_image_end.setText("Size:" + Formatter.formatShortFileSize(SuperResolutionActivity.this,finalFile.length()));
//                card_size_end.setVisibility(View.VISIBLE);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        });
//
//
//    }
    public void eventLoadImame(){
        cardView1.setOnClickListener(v -> {
           // requestPermissions();
            ImagePicker.with(SuperResolutionActivity.this)
                    .galleryOnly()
//                    .cameraOnly()
//                    .cropSquare()
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(2048)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Uri uri = data.getData();
        final InputStream imageStream;

        try {
            imageStream = getContentResolver().openInputStream(uri);
            final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imageStart.setImageBitmap(bitmap);

            //    imageStart.setImageURI(uri);

            originalImage = new File(uri.getPath());
            size_image_start.setText("Size: " + Formatter.formatShortFileSize(SuperResolutionActivity.this, originalImage.length()));
            card_size_start.setVisibility(View.VISIBLE);
            URI = uri.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

//    public void requestPermissions(){
//        PermissionListener permissionlistener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                Toast.makeText(SuperResolutionActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
//                openImage();
//            }
//
//            @Override
//            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(SuperResolutionActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//            }
//        };
//        TedPermission.create()
//                .setPermissionListener(permissionlistener)
//                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .check();
//
//    }
//    public void openImage(){
//        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener(){
//
//            @Override
//            public void onImageSelected(Uri uri) {
//                try {
////                   Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                    final InputStream imageStream = getContentResolver().openInputStream(uri);
//                    final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//                    imageStart.setImageBitmap(bitmap);
//                    originalImage = new File(uri.getPath().replace("raw/",""));
//                    size_image_start.setText("Size: " + Formatter.formatShortFileSize(SuperResolutionActivity.this, originalImage.length()));
//                    card_size_start.setVisibility(View.VISIBLE);
//                    URI = uri.getPath();
//                }catch (Exception e){
//                    Toast.makeText(SuperResolutionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
//
//        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(SuperResolutionActivity.this)
//                .setOnImageSelectedListener(listener).create();
//        tedBottomPicker.show(getSupportFragmentManager());
//    }
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