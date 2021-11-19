package com.ul.lj.si.vteamtrack.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.fragments.PostsFragment;
import com.ul.lj.si.vteamtrack.fragments.ProfileFragment;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Training;
import entities.User;
import viewModels.UserModel;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private Activity activity;
    private UserModel userModel;
    private ActivityResultLauncher<Intent> launchActivity;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public UsersAdapter(List<User> users, Activity activity) {
        this.users=users;
        this.activity = activity;


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView userSurname;
        private ImageButton viewProfile;
        private Button viewPosts;
        private ImageView image;

        public ViewHolder(View itemView) {

            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.item_user_name);
            userSurname = (TextView) itemView.findViewById(R.id.item_user_surname);
            viewProfile = (ImageButton) itemView.findViewById(R.id.item_user_profile);
            viewPosts = (Button) itemView.findViewById(R.id.item_user_posts);
            image = (ImageView) itemView.findViewById(R.id.item_user_profile_image);


        }
    }


    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        userModel = new ViewModelProvider((FragmentActivity) activity).get(UserModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_user, parent, false);

        // Return a new holder instance
        UsersAdapter.ViewHolder viewHolder = new UsersAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        TextView userName = holder.userName;
        TextView surname= holder.userSurname;
        ImageButton viewProfile = holder.viewProfile;
        Button viewPosts = holder.viewPosts;
        ImageView image = holder.image;


        userName.setText(user.getFirstName());
        surname.setText(user.getLastName());
        String imageUri = user.getImageUri();
        System.out.println("user image URi"+user.getImageUri());

        if(imageUri!=null && !imageUri.equals("")){
            System.out.println("user image URi"+Uri.parse(imageUri));
            /*image.setImageURI(Uri.parse(imageUri));
            Glide.with(activity.getApplicationContext())
                    .load(Uri.parse(imageUri))
                    .into(image);*/
        }

        viewPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", user.getId());
                bundle.putString("source", "individualPost");
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new PostsFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", user.getId());
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}