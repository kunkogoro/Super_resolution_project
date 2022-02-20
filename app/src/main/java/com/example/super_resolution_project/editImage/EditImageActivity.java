package com.example.super_resolution_project.editImage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;
import com.example.super_resolution_project.R;
import com.example.super_resolution_project.superResolution.SuperResolutionActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditImageActivity extends AppCompatActivity {

    private Button btn_pick;
    private ImageView img;
//    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if (result.getResultCode() == RESULT_OK){
//                Intent intent1 = result.getData();
//                Uri uri = intent1.getData();
//                switch (result.getResultCode()){
//                    case 100:
//                        Intent intent = new Intent(EditImageActivity.this, DsPhotoEditorActivity.class);
//                        intent.setData(uri);
//                        intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,"Images");
//                        intent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#00102A"));
//                        intent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR,Color.parseColor("#FFFFF"));
//                        intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,new int[]{DsPhotoEditorActivity.TOOL_WARMTH,DsPhotoEditorActivity.TOOL_PIXELATE});
//                        startActivityForResult(intent,101);
//                        System.out.println("do 2");
//                        break;
//                    case 101:
//                        img.setImageURI(uri);
//                        Toast.makeText(EditImageActivity.this, "Photo seved", Toast.LENGTH_SHORT).show();
//                        System.out.println("do 3");
//                        break;
//
//            }}
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        getView();
        eventPick();
    }
    void getView(){
        btn_pick = findViewById(R.id.button);
        img = findViewById(R.id.imageView_edit);

    }
    void eventPick(){
        btn_pick.setOnClickListener(v -> {
//            chessPermisson();
            ImagePicker.with(EditImageActivity.this)
                    .galleryOnly()
//                    .cameraOnly()
                    .cropSquare()
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
            img.setImageBitmap(bitmap);

//            originalImage = new File(uri.getPath());
//            size_image_start.setText("Size: " + Formatter.formatShortFileSize(SuperResolutionActivity.this, originalImage.length()));
//            card_size_start.setVisibility(View.VISIBLE);
//            URI = uri.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private void chessPermisson() {
//        int permission = ActivityCompat.checkSelfPermission(EditImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//        pickImage();
//        }else {
//            if (permission != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(EditImageActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
//            }else {
//                pickImage();
//            }
//        }
//    }
//    private void pickImage(){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
////       activityResultLauncher.launch(intent);
//        startActivityForResult(intent,100);
//       System.out.println("do 1");
//    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//            pickImage();
//        }else {
//            Toast.makeText(this, "Permisson Denied", Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_OK){
//            Uri uri = data.getData();
//            switch (requestCode){
//                case 100:
//                    Intent intent = new Intent(EditImageActivity.this, DsPhotoEditorActivity.class);
//                    intent.setData(uri);
//                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,"Images");
//                    intent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR, Color.parseColor("#FF6200EE"));
//                    intent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR,Color.parseColor("#FFFFFF"));
//                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE,new int[]{DsPhotoEditorActivity.TOOL_WARMTH,DsPhotoEditorActivity.TOOL_PIXELATE});
//                    startActivityForResult(intent,101);
//                    System.out.println("do 2");
//                    break;
//                case 101:
//                    img.setImageURI(uri);
//                    Toast.makeText(this, "Photo seved", Toast.LENGTH_SHORT).show();
//                    System.out.println("do 3");
//                    break;
//
//            }
//        }
//    }
}