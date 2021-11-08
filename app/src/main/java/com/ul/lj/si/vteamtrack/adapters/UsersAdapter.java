package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.fragments.PostsFragment;
import com.ul.lj.si.vteamtrack.fragments.ProfileFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import entities.Training;
import entities.User;
import viewModels.UserModel;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> users;
    private Activity activity;
    private UserModel userModel;

    public UsersAdapter(List<User> users, Activity activity) {
        this.users=users;
        this.activity = activity;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView userName;
        private TextView userSurname;
        private Button viewProfile;
        private Button viewPosts;

        public ViewHolder(View itemView) {

            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.item_user_name);
            userSurname = (TextView) itemView.findViewById(R.id.item_user_surname);
            viewProfile = (Button) itemView.findViewById(R.id.item_user_profile);
            viewPosts = (Button) itemView.findViewById(R.id.item_user_posts);
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
        Button viewProfile = holder.viewProfile;
        Button viewPosts = holder.viewPosts;

        userName.setText(user.getFirstName());
        surname.setText(user.getLastName());
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