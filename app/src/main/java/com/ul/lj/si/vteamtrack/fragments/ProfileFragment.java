package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

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
    private Button makeAdmin;
    private LinearLayout toggleLayout;
    private  LinearLayout changePassContainer;
    private EditText editName;
    private Button updateProfile;
    private Button unregisterUser;
    private EditText editSurname;
    private EditText editEmail;
    private EditText editPhone;
    private EditText editDateOfBirth;

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
        if (container != null) {
            container.removeAllViews();
        }
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
        makeAdmin = view.findViewById(R.id.btn_profile_admin);
        unregisterUser = view.findViewById(R.id.btn_profile_unregister);

        edit = view.findViewById(R.id.btn_profile_edit);
        updateProfile = view.findViewById(R.id.btn_profile_update);
        editName = view.findViewById(R.id.user_profile_name_edit);
        editSurname = view.findViewById(R.id.user_profile_surname_edit);
        editEmail = view.findViewById(R.id.user_profile_email_edit);
        editPhone = view.findViewById(R.id.user_profile_phone_edit);
        editDateOfBirth = view.findViewById(R.id.user_profile_dateOfBirth_edit);

        name.setText(user.firstName);
        surname.setText(user.lastName);
        email.setText(user.email);
        phone.setText(user.phoneNumber);
        dateOfBirth.setText(user.dateOfBirth);

        if(!user.userRole.equals("trainer") &&
        PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("trainer")
        && user.id!=currentUserId){
            makeAdmin.setVisibility(View.VISIBLE);
            if(!user.userRole.equals("admin")){
                makeAdmin.setText("Give admin permissions");
            }
            else {
                makeAdmin.setText("Take away admin permissions");
            }
        }
        if(!user.userRole.equals("trainer") &&
                !PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("player")
                && user.id!=currentUserId){
                unregisterUser.setVisibility(View.VISIBLE);
        }


        makeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!user.userRole.equals("admin")){
                    user.userRole="admin";
                    makeAdmin.setText("Take away admin permissions");
                }
                else {
                    user.userRole="player";
                    makeAdmin.setText("Give admin permissions");
                }
                userModel.update(user);
            }
        });
        unregisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModel.deleteUser(user);
                FragmentManager fm = (getActivity()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new HomeFragment();
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        if(currentUserId == userId){
            toggleLayout = view.findViewById(R.id.layout_profile_third);
            toggleLayout.setVisibility(View.VISIBLE);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setVisibility(View.GONE);
                updateProfile.setVisibility(View.VISIBLE);

                name.setVisibility(View.GONE);
                editName.setVisibility(View.VISIBLE);
                editName.setText(name.getText().toString());

                surname.setVisibility(View.GONE);
                editSurname.setVisibility(View.VISIBLE);
                editSurname.setText(surname.getText().toString());

                email.setVisibility(View.GONE);
                editEmail.setVisibility(View.VISIBLE);
                editEmail.setText(email.getText().toString());

                phone.setVisibility(View.GONE);
                editPhone.setVisibility(View.VISIBLE);
                editPhone.setText(phone.getText().toString());

                dateOfBirth.setVisibility(View.GONE);
                editDateOfBirth.setVisibility(View.VISIBLE);
                editDateOfBirth.setText(dateOfBirth.getText().toString());
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataValidation()) {
                    user.firstName = editName.getText().toString();
                    user.lastName = editSurname.getText().toString();
                    user.email = editEmail.getText().toString();
                    user.dateOfBirth = editDateOfBirth.getText().toString();
                    user.phoneNumber = editPhone.getText().toString();


                    userModel.update(user);

                    editName.setVisibility(View.GONE);
                    name.setVisibility(View.VISIBLE);
                    name.setText(user.firstName.toString());

                    editSurname.setVisibility(View.GONE);
                    surname.setVisibility(View.VISIBLE);
                    surname.setText(user.lastName.toString());

                    editEmail.setVisibility(View.GONE);
                    email.setVisibility(View.VISIBLE);
                    email.setText(user.email.toString());

                    editPhone.setVisibility(View.GONE);
                    phone.setVisibility(View.VISIBLE);
                    phone.setText(user.phoneNumber);

                    editDateOfBirth.setVisibility(View.GONE);
                    dateOfBirth.setVisibility(View.VISIBLE);
                    dateOfBirth.setText(user.dateOfBirth);

                    updateProfile.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Profile information updated", Toast.LENGTH_LONG).show();
                }
            }

        });
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

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean dataValidation(){
        boolean returnVaue=true;

        if(isEmpty(editEmail)){
            editEmail.setError("Email can not be empty");
            returnVaue=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()){
            editEmail.setError("Please enter a valid email");
            returnVaue=false;
        }
        else {
              User  user = userModel.checkUserCred(email.getText().toString(),PreferenceData.getTeam(getContext().getApplicationContext()));
              if(user!=null){
                  editEmail.setError("That email adress is already taken");
              }
        }
        if(isEmpty(editName)){
            editName.setError("Name can not be empty");
            returnVaue=false;
        }
        if(isEmpty(editSurname)){
            editSurname.setError("Surname can not be empty");
            returnVaue=false;
        }
        if(isEmpty(editPhone)){
            editPhone.setError("Phone number can not be empty");
            returnVaue=false;
        }
        if(isEmpty(editDateOfBirth)){
            editDateOfBirth.setError("Date of birth can not be empty");
            returnVaue=false;
        }
        return returnVaue;
    }
}
