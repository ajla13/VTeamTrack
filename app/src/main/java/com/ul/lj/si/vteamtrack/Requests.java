package com.ul.lj.si.vteamtrack;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.ul.lj.si.vteamtrack.adapters.RequestsAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import viewModels.UserModel;


public class Requests extends AppCompatActivity {

    private UserModel userModel;
    private ListView listView;
    RequestsAdapter reqAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_requests);
        userModel = new ViewModelProvider(this).get(UserModel.class);

        ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getUnconfirmedUsers().getValue();


        listView = (ListView)findViewById(R.id.regRequest);
        listView.setAdapter(reqAdapter);

        if (arrayOfUsers != null){

            reqAdapter = new RequestsAdapter(this,this,arrayOfUsers);
            listView.setAdapter(reqAdapter);
        }else{
            Log.d("gwyd","user list was null");
            reqAdapter = new RequestsAdapter(this,this,new ArrayList<User>());
            listView.setAdapter(reqAdapter);
        }
        userModel.getUnconfirmedUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                   reqAdapter.setUsers(users);
                    reqAdapter.clear();
                    reqAdapter.addAll(users);
                } else {
                    Log.d("gwyd", "no users found in db");
                    reqAdapter.setUsers(new ArrayList<User>());
                }
            }
        });

    }
}
