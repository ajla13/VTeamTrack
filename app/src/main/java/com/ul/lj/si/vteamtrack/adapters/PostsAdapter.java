package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.fragments.HomeFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Comment;
import entities.Post;
import entities.User;
import viewModels.CommentModel;
import viewModels.PostModel;
import viewModels.UserModel;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private User authorUser;
    private UserModel userModel;
    private Activity activity;
    private List<Post> posts;
    private AlertDialog.Builder builder;
    private PostModel postModel;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView content;
        public TextView author;
        public TextView date;
        public RecyclerView commentView;
        public EditText createComment;
        public Button postComment;
        public ImageButton deletePost;

        public ViewHolder(View itemView) {

            super(itemView);

           content = (TextView) itemView.findViewById(R.id.item_post_content);
           author = (TextView) itemView.findViewById(R.id.item_post_author);
           date = (TextView)  itemView.findViewById(R.id.item_post_date);
           commentView = (RecyclerView) itemView.findViewById(R.id.rvComments);
           createComment = (EditText) itemView.findViewById(R.id.new_comment);
           postComment = (Button) itemView.findViewById(R.id.btn_create_comment);
           deletePost = (ImageButton) itemView.findViewById(R.id.btn_delete_post);
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
        postModel = new ViewModelProvider((FragmentActivity) activity).get(PostModel.class);

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
        builder = new AlertDialog.Builder(activity);

        PreferenceData.setCurentPostId(activity.getApplicationContext(), post.getId());
        CommentModel commentModel = new ViewModelProvider((FragmentActivity)activity).get(CommentModel.class);

        authorUser = userModel.getUser(post.getAuthorId());
        // Set item views based on your views and data model
        TextView content = holder.content;
        TextView author = holder.author;
        TextView date = holder.date;
        RecyclerView rvComments = holder.commentView;
        EditText createComment = holder.createComment;
        Button postComment = holder.postComment;
        ImageButton deletePost = holder.deletePost;
        if(PreferenceData.getLoggedInUser(activity.getApplicationContext())==post.getAuthorId()){
            deletePost.setVisibility(View.VISIBLE);
        }

        content.setText(post.getContent());
        author.setText(authorUser.getFirstName()+ " "+authorUser.getLastName());


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(post.getDate().getTime());
        date.setText(sdf.format(utilDate));

        ArrayList<Comment> comments = (ArrayList<Comment>) commentModel.getCommentListByPost(post.getId());
        System.out.println("comments list "+ comments.size());
        CommentAdapter commentAdapter = new CommentAdapter(comments, activity);
        rvComments.setAdapter(commentAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(activity));

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage(R.string.post_delete)
                        .setTitle(R.string.delete)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                postModel.deletePost(post);
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

        postComment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String content = createComment.getText().toString();
                int authorId = PreferenceData.getLoggedInUser(activity.getApplicationContext());

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                LocalDate date = LocalDate.now();

                String _day = String.valueOf(date.getDayOfMonth());
                String _month = String.valueOf(date.getMonthValue());
                String _year = String.valueOf(date.getYear());

                StringBuilder dateTemp = new StringBuilder().append(_day).append("/").append(_month).append("/").append(_year).append(" ");
                String dateT = dateTemp.toString();
                Date dateComment = new Date();
                try {
                    dateComment=new SimpleDateFormat("dd/MM/yyyy").parse(dateT);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String teamName=PreferenceData.getTeam(activity.getApplicationContext());
                Comment comment = new Comment(authorId,post.getId(), content,dateComment);
                commentModel.createComment(comment);
                createComment.setText("");
                ArrayList<Comment> commentsUpdated = (ArrayList<Comment>) commentModel.getCommentListByPost(post.getId());
                CommentAdapter commentAdapter = new CommentAdapter(commentsUpdated, activity);
                rvComments.setAdapter(commentAdapter);
                rvComments.setLayoutManager(new LinearLayoutManager(activity));

            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
