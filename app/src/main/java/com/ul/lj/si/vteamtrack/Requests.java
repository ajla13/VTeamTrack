package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ul.lj.si.vteamtrack.adapters.RequestsAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import viewModels.UserModel;


public class Requests extends AppCompatActivity {

    private UserModel userModel;
    private RequestsAdapter reqAdapter;
    private Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_requests);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        activity= this;
        ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getUnconfirmedUsers().getValue();

        RecyclerView rvRequests = (RecyclerView) findViewById(R.id.regRequest);


        if (arrayOfUsers != null){

            reqAdapter = new RequestsAdapter(arrayOfUsers,this);
            rvRequests.setAdapter(reqAdapter);
            rvRequests.setLayoutManager(new LinearLayoutManager(this));
        }else{
            Log.d("gwyd","user list was null");
            reqAdapter = new RequestsAdapter(new ArrayList<User>(),this);
            rvRequests.setAdapter(reqAdapter);
            rvRequests.setLayoutManager(new LinearLayoutManager(this));
        }
        userModel.getUnconfirmedUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                    reqAdapter = new RequestsAdapter(users,activity);
                    rvRequests.setAdapter(reqAdapter);
                    rvRequests.setLayoutManager(new LinearLayoutManager(activity));
                } else {
                    Log.d("gwyd", "no users found in db");
                    reqAdapter = new RequestsAdapter(new ArrayList<User>(),activity);
                    rvRequests.setAdapter(reqAdapter);
                    rvRequests.setLayoutManager(new LinearLayoutManager(activity));
                }
                if(users.isEmpty()){
                    TextView empty = findViewById(R.id.empty_view_req);
                    rvRequests.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                else {
                    TextView empty = findViewById(R.id.empty_view_req);
                    rvRequests.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        });

    }
}
