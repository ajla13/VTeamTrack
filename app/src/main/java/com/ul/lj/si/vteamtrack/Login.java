package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.mindrot.jbcrypt.BCrypt;

import entities.Team;
import entities.User;
import viewModels.TeamModel;
import viewModels.UserModel;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText teamName;
    TeamModel teamModel;
    UserModel userModel;
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        userModel = new ViewModelProvider(this).get(UserModel.class);


        email=(EditText) findViewById(R.id.login_email);
        password=(EditText) findViewById(R.id.login_password);
        teamName=(EditText) findViewById(R.id.login_team);
        Button login=(Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if(dataValidation()){
                       if(!creditentials()){
                           Toast.makeText(getApplicationContext(),
                                   "The entered creditentials are incorrect", Toast.LENGTH_LONG).show();
                       }
                       else {
                           if(BCrypt.checkpw(password.getText().toString(), user.password)) {
                               PreferenceData.setUserLoggedInStatus(getApplicationContext(),true);
                               PreferenceData.setLoggedInUserEmail(getApplicationContext(),email.getText().toString());
                               PreferenceData.setLoggedInUser(getApplicationContext(), user.id);
                               PreferenceData.setLoggedInUserRole(getApplicationContext(),user.userRole);
                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                               startActivity(intent);
                           }
                           else {
                               Toast.makeText(getApplicationContext(),
                                       "The entered creditentials are incorrect", Toast.LENGTH_LONG).show();
                           }

                       }
                   }
            }
        });

    }



    boolean creditentials(){

        user = userModel.checkUserCred(email.getText().toString(),teamName.getText().toString());

        if(user == null){
            return false;
        }
        return true;
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean dataValidation(){
        boolean returnVaue=true;
        if(isEmpty(teamName)){
            teamName.setError("Name of the team can not be empty");
            returnVaue=false;
        }
        if(isEmpty(password)){
            password.setError("Password can not be empty");
            returnVaue=false;
        }
        if(isEmpty(email)){
            email.setError("Email can not be empty");
            returnVaue=false;
        }
        return returnVaue;
    }

    public void switchToRegistration(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}
