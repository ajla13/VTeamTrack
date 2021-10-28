package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageButton expand = (ImageButton) convertView.findViewById(R.id.btn_expand_req);
        Button accept = (Button) convertView.findViewById(R.id.btn_req_accept);
        Button decline = (Button) convertView.findViewById(R.id.btn_req_decline);
        LinearLayout toggleLayout = convertView.findViewById(R.id.item_req_secondlayout);
        // Populate the data into the template view using the data object
        userName.setText(user.firstName);
        userSurname.setText(user.lastName);
        dateOfBirth.setText(user.dateOfBirth);
        phone.setText(user.phoneNumber);
        email.setText(user.email);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.registrationConfirmed = true;
                userModel.update(user);
                Toast.makeText(activity.getApplicationContext(),
                        "User registration accepted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModel.deleteUser(user);
                Toast.makeText(activity.getApplicationContext(),
                        "User registration declined", Toast.LENGTH_LONG).show();

            }
        });
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float deg = expand.getRotation() + 180F;
                expand.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

                if(toggleLayout.getVisibility()==View.GONE){
                    toggleLayout.setVisibility(View.VISIBLE);
                    dateOfBirth.setVisibility((dateOfBirth.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    email.setVisibility((email.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    phone.setVisibility((phone.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                }
                else {
                    dateOfBirth.setVisibility((dateOfBirth.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    email.setVisibility((email.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    phone.setVisibility((phone.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                    toggleLayout.setVisibility(View.GONE);
                }
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}
