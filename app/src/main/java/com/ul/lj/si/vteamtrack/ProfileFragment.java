package com.ul.lj.si.vteamtrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.mindrot.jbcrypt.BCrypt;

import entities.User;
import viewModels.UserModel;

public class ProfileFragment extends Fragment {
    private int userId;
    private UserModel userModel;
    private User user;
    private TextView name;
    private TextView surname;
    private TextView email;
    private TextView phone;
    private TextView dateOfBirth;
    private EditText oldPass;
    private EditText newPass;
    private Button edit;
    private Button changePass;
    private int currentUserId;
    private Button cancelPass;
    private  Button updatePass;
    private LinearLayout toggleLayout;
    private  LinearLayout changePassContainer;

    public ProfileFragment(int userId){
        this.userId = userId;
    }
    public ProfileFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new ViewModelProvider(this).get(UserModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

        currentUserId = PreferenceData.getLoggedInUser(getActivity().getApplicationContext());
        userId = this.getArguments().getInt("userId");

        user = userModel.getUser(userId);
        name = (TextView) view.findViewById(R.id.user_profile_name);
        surname = (TextView) view.findViewById(R.id.user_profile_surname);
        email = (TextView) view.findViewById(R.id.user_profile_email);
        phone = (TextView) view.findViewById(R.id.user_profile_phone);
        dateOfBirth= (TextView) view.findViewById(R.id.user_profile_dateOfBirth);
        oldPass = (EditText) view.findViewById(R.id.profile_old_pass);
        newPass = view.findViewById(R.id.profile_new_pass);
        changePassContainer = view.findViewById(R.id.profile_layout_changePass);
        updatePass = view.findViewById(R.id.btn_profile_updatePass);
        cancelPass = view.findViewById(R.id.btn_profile_cancelPass);
        changePass = view.findViewById(R.id.btn_profile_changePass);

        name.setText(user.firstName);
        surname.setText(user.lastName);
        email.setText(user.email);
        phone.setText(user.phoneNumber);
        dateOfBirth.setText(user.dateOfBirth);

        if(currentUserId == userId){
            toggleLayout = view.findViewById(R.id.layout_profile_third);
            toggleLayout.setVisibility(View.VISIBLE);
        }
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserPass();
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassContainer.setVisibility(View.VISIBLE);
            }
        });
        cancelPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldPass.setText("");
                newPass.setText("");
                changePassContainer.setVisibility(View.GONE);
            }
        });

        return  view;
    }
    public void changeUserPass() {
        String status="";
        String userOldPass=oldPass.getText().toString();
        String userNewPass = newPass.getText().toString();
        if(BCrypt.checkpw(userOldPass, user.password)){
            if(!userNewPass.equals(userOldPass)){
                user.password = BCrypt.hashpw(userNewPass, BCrypt.gensalt());
                userModel.update(user);
                status = "Password changed.";

            }
          else {
              status="The new password is the same as the current password.";
            }
        }
        else {
            status="Incorrect old password.";
        }
        Toast.makeText(getActivity().getApplicationContext(),
                status, Toast.LENGTH_LONG).show();
    }
}
