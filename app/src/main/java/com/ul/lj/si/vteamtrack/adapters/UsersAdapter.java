package com.ul.lj.si.vteamtrack.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.fragments.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import entities.User;

public class UsersAdapter extends ArrayAdapter<User> {

    private List<User> users;
    private  Context context;

    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.context= context;

    }
    
    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.item_user_name);
        TextView userSurname = (TextView) convertView.findViewById(R.id.item_user_surname);
        Button viewProfile = convertView.findViewById(R.id.item_user_profile);
        // Populate the data into the template view using the data object
        userName.setText(user.firstName);
        userSurname.setText(user.lastName);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("userId", user.id);
                FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}