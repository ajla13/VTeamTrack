package com.ul.lj.si.vteamtrack.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

import entities.Comment;
import entities.User;
import viewModels.CommentModel;
import viewModels.UserModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{

    private Activity activity;
    private List<Comment> comments;
    private UserModel userModel;
    private CommentModel commentModel;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView content;
        public TextView author;
        public TextView date;
        public ImageButton delete;

        public ViewHolder(View itemView) {

            super(itemView);

            content = (TextView) itemView.findViewById(R.id.item_comment_content);
            author = (TextView) itemView.findViewById(R.id.item_comment_author);
            date = (TextView)  itemView.findViewById(R.id.item_comment_date);
            delete = (ImageButton) itemView.findViewById(R.id.btn_delete_comment);
        }
    }

    public CommentAdapter(List<Comment> comments, Activity activity){
        this.comments=comments;
        this.activity=activity;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        userModel = new ViewModelProvider((FragmentActivity) activity).get(UserModel.class);
        commentModel = new ViewModelProvider((FragmentActivity) activity).get(CommentModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.comment_item, parent, false);

        // Return a new holder instance
        CommentAdapter.ViewHolder viewHolder = new CommentAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        User user = userModel.getUser(comment.getAuthorId());
        TextView content = holder.content;
        TextView author = holder.author;
        TextView date = holder.date;
        ImageButton delete = holder.delete;

        if(comment.getAuthorId()== PreferenceData.getLoggedInUser(activity.getApplication())){
            delete.setVisibility(View.VISIBLE);
        }

        content.setText(comment.getContent().toString());
        author.setText(user.getFirstName()+ " "+user.getLastName());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(comment.getDate().getTime());
        date.setText(sdf.format(utilDate));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentModel.delete(comment);
                 comments.remove(position);
                 notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
