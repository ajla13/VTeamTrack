package com.ul.lj.si.vteamtrack;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ul.lj.si.vteamtrack.fragments.RegistrationFormFragment;
import com.ul.lj.si.vteamtrack.fragments.RegistrationInitialFragment;


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
    public void playerReg(View v){
        registrationType="player";
        replaceFragment(new RegistrationFormFragment(registrationType));
    }
    public void trainerReg(View v){
        registrationType="trainer";
        replaceFragment(new RegistrationFormFragment(registrationType));
    }

}
