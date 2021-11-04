package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.Post;
import entities.User;
import viewModels.UserModel;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    User authorUser;
    UserModel userModel;
    SimpleDateFormat sdf;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView content;
        public TextView author;
        public TextView date;

        public ViewHolder(View itemView) {

            super(itemView);

           content = (TextView) itemView.findViewById(R.id.item_post_content);
           author = (TextView) itemView.findViewById(R.id.item_post_author);
           date = (TextView)  itemView.findViewById(R.id.item_post_date);
        }
    }

    private List<Post> posts;

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
        authorUser = userModel.getUser(post.authorId);
        // Set item views based on your views and data model
        TextView content = holder.content;
        TextView author = holder.author;
        TextView date = holder.date;
        content.setText(post.content);
        author.setText(authorUser.firstName);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(post.date.getTime());
        date.setText(sdf.format(utilDate));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
