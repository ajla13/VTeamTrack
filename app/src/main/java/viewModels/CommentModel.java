package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import database.AppDatabase;
import entities.Comment;
import repository.CommentRepo;

public class CommentModel extends AndroidViewModel {
    private CommentRepo commentRepo;
    private LiveData<List<Comment>> commentsByPost;

    public CommentModel(@NonNull Application application) {
        super(application);
        commentRepo = new CommentRepo(application);
     //   commentsByPost = commentRepo.getCommentsByPost();

    }
   /* public LiveData<List<Comment>> getCommentsByPost(){
        System.out.println("postId in model"+ PreferenceData.getPostId(getApplication().getApplicationContext()));

        if(commentsByPost==null){
            commentsByPost = commentRepo.getCommentsByPost();
        }
        return commentsByPost;
    }*/
    public Comment getComment(int id){
        return commentRepo.getComment(id);
    }
    public Comment createComment(Comment comment){
        commentRepo.insert(comment);
        return comment;
    }
    public List<Comment> getCommentListByPost(int postId) {
        return commentRepo.getCommentListByPost(postId);
    }
    public void delete(Comment comment){
        commentRepo.delete(comment);
    }
    public void update(Comment comment){
        commentRepo.update(comment);
    }
}
