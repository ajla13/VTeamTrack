package com.ul.lj.si.vteamtrack.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.PostsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.Post;
import viewModels.PostModel;


public class PostsFragment extends Fragment {

    ArrayList<Post> posts;
    PostModel postModel;
    PostsAdapter adapter;
    public Button createPost;
    public EditText postContent;
    public String source;
    public int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.posts_list, container, false);
        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        postModel = new ViewModelProvider(this).get(PostModel.class);
        createPost= view.findViewById(R.id.btn_create_post);
        postContent = view.findViewById(R.id.new_post);

        source = this.getArguments().getString("source");
        if(source.equals("navigation")){
            posts = (ArrayList<Post>) postModel.getPosts().getValue();
        }
        else {
            userId = this.getArguments().getInt("userId");
            posts = (ArrayList<Post>) postModel.getPostsByAuthor(userId);
            createPost.setVisibility(View.GONE);
            postContent.setVisibility(View.GONE);
        }



        if (posts != null){
            adapter = new PostsAdapter(posts, getActivity());
            rvPosts.setAdapter(adapter);
            rvPosts.setLayoutManager((new LinearLayoutManager(getActivity())));
        }else{
            Log.d("gwyd","posts list was null");
            adapter = new PostsAdapter(new ArrayList<Post>(), getActivity());
            rvPosts.setAdapter(adapter);
            rvPosts.setLayoutManager((new LinearLayoutManager(getActivity())));
        }

        if(source.equals("navigation")) {
            postModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
                @Override
                public void onChanged(@Nullable List<Post> posts) {

                    adapter = new PostsAdapter(posts, getActivity());
                    rvPosts.setAdapter(adapter);
                    rvPosts.setLayoutManager((new LinearLayoutManager(getActivity())));


                }
            });

         createPost.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.O)
             @Override
             public void onClick(View v) {
                 Post post = new Post();
                 post.content = postContent.getText().toString();
                 post.authorId = PreferenceData.getLoggedInUser(getActivity().getApplicationContext());

                 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


                 LocalDate date = LocalDate.now();
                 String _day = String.valueOf(date.getDayOfMonth());
                 String _month = String.valueOf(date.getMonthValue());
                 String _year = String.valueOf(date.getYear());

                 StringBuilder dateTemp = new StringBuilder().append(_day).append("/").append(_month).append("/").append(_year).append(" ");
                 System.out.println(dateTemp);
                 String dateT = dateTemp.toString();
                 System.out.println(dateT);
                 try {
                     post.date=new SimpleDateFormat("dd/MM/yyyy").parse(dateT);
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
                 post.likes=0;
                 post.teamName=PreferenceData.getTeam(getActivity().getApplicationContext());
                 postModel.createPost(post);
                 postContent.setText("");

             }
         });
    }

        return view;

    }
}
