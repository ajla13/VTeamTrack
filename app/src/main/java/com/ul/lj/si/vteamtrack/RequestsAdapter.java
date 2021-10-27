package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.User;
import viewModels.GameModel;
import viewModels.UserModel;

public class RequestsAdapter extends ArrayAdapter<User> {

    private Activity activity;

    private List<User> users;

    private UserModel userModel;

    public RequestsAdapter(Activity activity, Context context, ArrayList<User> users) {

        super(context, 0, users);
        this.activity = activity;
        userModel = new ViewModelProvider((FragmentActivity) activity).get(UserModel.class);
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.request_item, parent, false);
        }
        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.item_req_name);
        TextView userSurname = (TextView) convertView.findViewById(R.id.item_req_surname);
        TextView email = (TextView) convertView.findViewById(R.id.item_req_email);
        TextView phone = (TextView) convertView.findViewById(R.id.item_req_phone);
        TextView dateOfBirth = (TextView) convertView.findViewById(R.id.item_req_dateOfBirth);
        // Populate the data into the template view using the data object
        userName.setText(user.firstName);
        userSurname.setText(user.lastName);
        dateOfBirth.setText(user.dateOfBirth);
        phone.setText(user.phoneNumber);
        email.setText(user.email);
        // Return the completed view to render on screen
        return convertView;
    }
}
