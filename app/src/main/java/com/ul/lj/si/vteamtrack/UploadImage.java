package com.ul.lj.si.vteamtrack;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.ul.lj.si.vteamtrack.fragments.PlayersListFragment;
import com.ul.lj.si.vteamtrack.fragments.RegistrationFormFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import entities.User;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import viewModels.UserModel;

public class UploadImage extends AppCompatActivity {

        private ImageView image;
        Uri selectedImage;
        Activity activity;
        private ActivityResultLauncher<Intent> launchActivity;
        private UserModel userModel;
        private int userId;
        private User user;
        private String source;

        private static final int REQUEST_EXTERNAL_STORAGE = 1;
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.upload_image);
            image = findViewById(R.id.img);
            activity=this;
            source= getIntent().getStringExtra("source");

            if(!source.equals("registration")){
                userId = getIntent().getIntExtra("userId", 0);
                userModel = new ViewModelProvider(this).get(UserModel.class);
                user = userModel.getUser(userId);

            }

            launchActivity = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        // Method to get the absolute path of the selected image from its URI
                        @Override
                        public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == RESULT_OK) {
                                    selectedImage = result.getData().getData();
                                    Glide.with(activity.getApplicationContext())
                                            .load(selectedImage)
                                            .into(image);
                                    }
                                }
                            }
                    );
        }

        // Method for starting the activity for selecting image from phone storage
        public void pick(View view) {
            verifyStoragePermissions(activity);
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            Intent.createChooser(intent,"Open Gallery");
            launchActivity.launch(intent);
        }
        public void upload(View view){

                Intent intent = new Intent();
                intent.setData(selectedImage);
                this.setResult(RESULT_OK, intent);
                finish();

        }

        public static void verifyStoragePermissions(Activity activity) {
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        activity,
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }
    }

