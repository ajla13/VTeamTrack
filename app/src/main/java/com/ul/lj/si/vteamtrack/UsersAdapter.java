package com.ul.lj.si.vteamtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import entities.User;

public class UsersAdapter extends ArrayAdapter<User> {

    private List<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
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
        // Populate the data into the template view using the data object
        userName.setText(user.firstName);
        userSurname.setText(user.lastName);
        // Return the completed view to render on screen
        return convertView;
    }
}