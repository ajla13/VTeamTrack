package com.ul.lj.si.vteamtrack.fragments;

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

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.PostsAdapter;
import com.ul.lj.si.vteamtrack.adapters.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.User;
import viewModels.UserModel;


public class PlayersListFragment extends Fragment {

    private UserModel userModel;
    private UsersAdapter userAdapter;


        @Nullable
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if (container != null) {
                container.removeAllViews();
            }
            View view = inflater.inflate(R.layout.listview, container, false);
            RecyclerView rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);

            userModel = new ViewModelProvider(this).get(UserModel.class);

            ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getPlayers().getValue();


            if (arrayOfUsers != null) {

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
