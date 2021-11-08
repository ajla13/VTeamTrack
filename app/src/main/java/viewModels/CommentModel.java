package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import database.AppDatabase;
import entities.Comment;
import repository.CommentRepo;

public class CommentModel extends AndroidViewModel {
    private CommentRepo commentRepo;
    private LiveData<List<Comment>> commentsByPost;

    public CommentModel(@NonNull Application application, int id) {
        super(application);
        commentRepo = new CommentRepo(application);
        commentsByPost = commentRepo.getCommentsByPost();

    }
    public LiveData<List<Comment>> getCommentsByPost(){
        if(commentsByPost==null){
            commentsByPost = commentRepo.getCommentsByPost();
        }
        return commentsByPost;
    }
    public Comment getComment(int id){
        return commentRepo.getComment(id);
    }
    public Comment createComment(Comment comment){
        commentRepo.insert(comment);
        return comment;
    }
    public void delete(Comment comment){
        commentRepo.delete(comment);
    }
    public void update(Comment comment){
        commentRepo.update(comment);
    }
}
