package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.ul.lj.si.vteamtrack.fragments.DatePickerFragment;
import com.ul.lj.si.vteamtrack.fragments.RegistrationFormFragment;
import com.ul.lj.si.vteamtrack.fragments.RegistrationInitialFragment;
import com.ul.lj.si.vteamtrack.fragments.RegistrationSupervisorFragment;


public class RegisterActivity extends AppCompatActivity {

    String registrationType;
    Fragment fragment;
    FragmentManager fm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        fragment= new RegistrationInitialFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_view, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }
    public void replaceFragment( Fragment fragment){
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container_view, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
    public void addFragment( Fragment fragment){
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container_view, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
    public void playerReg(View v){
        registrationType="player";
        Bundle args = new Bundle();
        args.putString("registrationType", registrationType);
        RegistrationFormFragment fragment = new RegistrationFormFragment();
        fragment.setArguments(args);
        replaceFragment(fragment);
    }
    public void trainerReg(View v){
        registrationType="trainer";
        Bundle args = new Bundle();
        args.putString("registrationType", registrationType);
        RegistrationFormFragment fragment = new RegistrationFormFragment();
        fragment.setArguments(args);
        replaceFragment(fragment);
    }
    public void supervisorReg(View v){
        RegistrationSupervisorFragment fragment = new RegistrationSupervisorFragment();
        replaceFragment(fragment);
    }
    public void showDatePickerDialog(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("viewId",v.getId());
        bundle.putString("source", "editText");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

}
