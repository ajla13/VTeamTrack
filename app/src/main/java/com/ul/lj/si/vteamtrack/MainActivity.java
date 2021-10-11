package com.ul.lj.si.vteamtrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import entities.User;
import viewModels.UserModel;

public class MainActivity extends AppCompatActivity {
    UserModel userModel;

    public void renderUpdates(){
        Observer<List<User>> liveDataObserver=new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                System.out.println("the users:");
                System.out.println(users);
            }
        };

        if(userModel!=null){

            userModel.getUsers().observe(this,liveDataObserver);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userModel = new ViewModelProvider(this).get(UserModel.class);
        this.renderUpdates();

    }
    public void insertUser(View view) {
        User user = new User();
        user.lastName="omelastname";
        user.firstName="somefirstname";
        userModel.createUser(user);
        TextView displayUser = (TextView) findViewById(R.id.displayUser);
        displayUser.setText(user.firstName + " " + user.lastName);

    }


}