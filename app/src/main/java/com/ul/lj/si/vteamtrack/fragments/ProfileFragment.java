package com.ul.lj.si.vteamtrack.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private SimpleDateFormat sdf;
    private AlertDialog.Builder builder;

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
        builder = new AlertDialog.Builder(getActivity());


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

        name.setText(user.getFirstName());
        surname.setText(user.getLastName());
        email.setText(user.getEmail());
        phone.setText(user.getPhoneNumber());

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(user.getDateOfBirth().getTime());
        dateOfBirth.setText(sdf.format(utilDate));


        if(!user.getUserRole().equals("trainer") &&
        PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("trainer")
        && user.getId()!=currentUserId){

            makeAdmin.setVisibility(View.VISIBLE);
            if(!user.getUserRole().equals("admin")){
                makeAdmin.setText("Give admin permissions");
            }
            else {
                makeAdmin.setText("Take away admin permissions");
            }
        }
        if(!user.getUserRole().equals("trainer") &&
                !PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("player")
                && user.getId()!=currentUserId){
                unregisterUser.setVisibility(View.VISIBLE);
        }


        makeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!user.getUserRole().equals("admin")){
                        builder.setMessage(R.string.make_admin_msg)
                                .setTitle(R.string.make_admin)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        user.setUserRole("admin");
                                        userModel.update(user);
                                        makeAdmin.setText("Take away admin permissions");
                                        Toast.makeText(getActivity().getApplicationContext(),
                                                "Given admin permissions to player " + user.getFirstName() +" " +
                                                        user.getLastName(), Toast.LENGTH_LONG).show();
                                    }
                                });
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                }
                else {
                    builder.setMessage(R.string.unset_admin_msg)
                            .setTitle(R.string.unset_admin)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    user.setUserRole("player");
                                    userModel.update(user);
                                    makeAdmin.setText("Give admin permissions");
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "Admin permission taken away from player " + user.getFirstName() +" " +
                                                    user.getLastName(), Toast.LENGTH_LONG).show();
                                }
                            });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                }
                AlertDialog dialog = builder.create();
                builder.show();
            }
        });

        unregisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(R.string.unregister_message)
                        .setTitle(R.string.unregister)
                        .setPositiveButton(R.string.unregister, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Player " + user.getFirstName() +" " +
                                                user.getLastName() + " unregistered", Toast.LENGTH_LONG).show();
                                userModel.deleteUser(user);
                                FragmentManager fm = (getActivity()).getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                Fragment fragment = new HomeFragment();
                                ft.replace(R.id.nav_fragment, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.commit();

                            }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                builder.show();

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
                    user.setFirstName(editName.getText().toString());
                    user.setLastName(editSurname.getText().toString());
                    user.setEmail(editEmail.getText().toString());

                    try {
                        user.setDateOfBirth(sdf.parse(editDateOfBirth.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    user.setPhoneNumber(editPhone.getText().toString());

                    userModel.update(user);

                    editName.setVisibility(View.GONE);
                    name.setVisibility(View.VISIBLE);
                    name.setText(user.getFirstName().toString());

                    editSurname.setVisibility(View.GONE);
                    surname.setVisibility(View.VISIBLE);
                    surname.setText(user.getLastName().toString());

                    editEmail.setVisibility(View.GONE);
                    email.setVisibility(View.VISIBLE);
                    email.setText(user.getEmail().toString());

                    editPhone.setVisibility(View.GONE);
                    phone.setVisibility(View.VISIBLE);
                    phone.setText(user.getPhoneNumber());

                    editDateOfBirth.setVisibility(View.GONE);
                    dateOfBirth.setVisibility(View.VISIBLE);
                    Date utilDate = new Date(user.getDateOfBirth().getTime());
                    dateOfBirth.setText(sdf.format(utilDate));

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
        if(BCrypt.checkpw(userOldPass, user.getPassword())){
            if(!userNewPass.equals(userOldPass)){
                user.setPassword(BCrypt.hashpw(userNewPass, BCrypt.gensalt()));
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
            User tempUser = userModel.checkUserCred(editEmail.getText().toString(),
                    PreferenceData.getTeam(getActivity().getApplicationContext()));
            if( tempUser!= null){
                if(user.getId()!=tempUser.getId()){
                    editEmail.setError("Email taken");
                    returnVaue=false;
                }
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
