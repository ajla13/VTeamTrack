package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Comment;
import entities.Post;
import entities.User;
import viewModels.CommentModel;
import viewModels.UserModel;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private User authorUser;
    private UserModel userModel;
    private CommentModel commentModel;
    private Activity activity;
    private List<Post> posts;
    private ArrayList<Comment> comments;
    private CommentAdapter commentAdapter;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView content;
        public TextView author;
        public TextView date;
        public RecyclerView commentView;

        public ViewHolder(View itemView) {

            super(itemView);

           content = (TextView) itemView.findViewById(R.id.item_post_content);
           author = (TextView) itemView.findViewById(R.id.item_post_author);
           date = (TextView)  itemView.findViewById(R.id.item_post_date);
           commentView = (RecyclerView) itemView.findViewById(R.id.rvComments);
        }
    }



    public PostsAdapter(List<Post> posts, Activity activity) {

        this.posts = posts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        userModel = new ViewModelProvider((FragmentActivity) activity).get(UserModel.class);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_post, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Post post = posts.get(position);
        PreferenceData.setCurentPostId(activity.getApplicationContext(), post.getId());
        commentModel = new ViewModelProvider((FragmentActivity)activity).get(CommentModel.class);

        //commentModel = new ViewModelProvider((FragmentActivity)activity).get(CommentModel.class);
        authorUser = userModel.getUser(post.getAuthorId());
        // Set item views based on your views and data model
        TextView content = holder.content;
        TextView author = holder.author;
        TextView date = holder.date;
        RecyclerView rvComments = holder.commentView;

        content.setText(post.getContent());
        author.setText(authorUser.getFirstName());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(post.getDate().getTime());
        date.setText(sdf.format(utilDate));

        comments = (ArrayList<Comment>) commentModel.getCommentsByPost().getValue();

        if (comments != null) {
            commentAdapter = new CommentAdapter(comments, activity);
            rvComments.setAdapter(commentAdapter);
            rvComments.setLayoutManager(new LinearLayoutManager(activity));
        } else {
            Log.d("gwyd", "comments list was null");
            commentAdapter = new CommentAdapter(new ArrayList<Comment>(), activity);
            rvComments.setAdapter(commentAdapter);
            rvComments.setLayoutManager(new LinearLayoutManager(activity));
        }
        commentModel.getCommentsByPost().observe
                ((FragmentActivity) activity, new Observer<List<Comment>>() {

                    @Override
                    public void onChanged(List<Comment> commentsObserved) {
                        if (commentsObserved != null) {
                            commentAdapter = new CommentAdapter(commentsObserved, activity);
                            rvComments.setAdapter(commentAdapter);
                            rvComments.setLayoutManager(new LinearLayoutManager(activity));
                        } else {
                            Log.d("gwyd", "comments list was null");
                            commentAdapter = new CommentAdapter(new ArrayList<Comment>(), activity);
                            rvComments.setAdapter(commentAdapter);
                            rvComments.setLayoutManager(new LinearLayoutManager(activity));
                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
