package com.ul.lj.si.vteamtrack.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.Login;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.PostsAdapter;
import com.ul.lj.si.vteamtrack.adapters.UsersAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entities.User;
import viewModels.UserModel;


public class PlayersListFragment extends Fragment {

    private UserModel userModel;
    private UsersAdapter userAdapter;
    private  byte[] imageByteArray;

        @Nullable
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (container != null) {
                container.removeAllViews();
            }

            View view = inflater.inflate(R.layout.listview, container, false);
            RecyclerView rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
            AssetManager am =getActivity().getAssets();
            try {
                InputStream is = am.open("avatar.jpg");
                imageByteArray= new byte[is.available()];
                is.read(imageByteArray);
                is.close();
                // use the input stream as you want
            } catch (IOException e) {
                e.printStackTrace();
            }
            userModel = new ViewModelProvider(this).get(UserModel.class);

            ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getPlayers().getValue();


            if (arrayOfUsers != null) {
                for (int i=0; i<arrayOfUsers.size();i++) {
                    arrayOfUsers.get(i).setImage(imageByteArray);
                }
                userAdapter = new UsersAdapter(arrayOfUsers, getActivity());
                rvUsers.setAdapter(userAdapter);
                rvUsers.setLayoutManager((new LinearLayoutManager(getActivity())));
            } else {
                Log.d("gwyd", "user list was null");
                userAdapter = new UsersAdapter(new ArrayList<User>(), getActivity());
                rvUsers.setAdapter(userAdapter);
                rvUsers.setLayoutManager((new LinearLayoutManager(getActivity())));
            }

            userModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable List<User> users) {
                    if (users != null) {
                        for (int i=0; i<users.size();i++) {
                            users.get(i).setImage(imageByteArray);
                        }
                        userAdapter = new UsersAdapter(users, getActivity());
                        rvUsers.setAdapter(userAdapter);
                        rvUsers.setLayoutManager((new LinearLayoutManager(getActivity())));
                    } else {
                        Log.d("gwyd", "no users found in db");
                        userAdapter = new UsersAdapter(new ArrayList<User>(), getActivity());
                        rvUsers.setAdapter(userAdapter);
                        rvUsers.setLayoutManager((new LinearLayoutManager(getActivity())));
                    }
                    if (users.isEmpty()) {
                        TextView empty = view.findViewById(R.id.empty_view_player);
                        rvUsers.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                    } else {
                        TextView empty = view.findViewById(R.id.empty_view_player);
                        rvUsers.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                    }

                }
            });
            return view;
        }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
