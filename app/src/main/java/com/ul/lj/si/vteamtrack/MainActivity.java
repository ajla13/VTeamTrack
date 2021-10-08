package com.ul.lj.si.vteamtrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import entities.User;
import viewModels.UserModel;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserModel userModel = new ViewModelProvider(this).get(UserModel.class);


    }
    public void insertUser(View view) {
     UserModel userModel = new ViewModelProvider(this).get(UserModel.class);
        User user = new User();
        user.firstName="Test_firstName";
        user.lastName="Test_lastname";
        userModel.createUser(user);
        TextView displayUser = (TextView) findViewById(R.id.displayUser);
        displayUser.setText(user.firstName + " " + user.lastName);


    }


}