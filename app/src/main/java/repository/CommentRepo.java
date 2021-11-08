package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import javax.inject.Inject;

import dao.CommentDao;
import database.AppDatabase;
import entities.Comment;


public class CommentRepo {

    private LiveData<List<Comment>> commentsByPost;
    private CommentDao commentDao;
    private Comment comment;
    private int postId;
    @Inject
    public CommentRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        commentDao = db.commentDao();
        postId = PreferenceData.getPostId(application.getApplicationContext());
        commentsByPost = commentDao.getCommentsByPost(postId);
    }


    public LiveData<List<Comment>> getCommentsByPost() {
        if(commentsByPost==null){
            AppDatabase.executor.execute(() -> {
                commentsByPost = commentDao.getCommentsByPost(postId);
            });
        }
        return commentsByPost;
    }

    public Comment getComment(int id){
        comment = commentDao.getComment(id);
        return comment;
    }
    public void insert(Comment comment){
        commentDao.insert(comment);
    }
    public void delete(Comment comment){
        commentDao.delete(comment);
    }
    public void update(Comment comment){
        commentDao.update(comment);
    }
}
