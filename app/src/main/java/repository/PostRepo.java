package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dao.PostDao;

import database.AppDatabase;
import entities.Post;


public class PostRepo {

    private LiveData<List<Post>> allPosts;
    private PostDao postDao;
    private Post post;
    private List<Post> postsByAuthor;
    String teamName;

    @Inject
    public PostRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        postDao = db.postDao();
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        allPosts=postDao.getAllPosts(teamName);

    }

    public LiveData<List<Post>> getAllPosts() {
        if (allPosts == null) {
            AppDatabase.executor.execute(() -> {
                allPosts = postDao.getAllPosts(teamName);
            });
        }
        return allPosts;
    }

    public List<Post>  getPostsByAuthor(int authorId) {

                postsByAuthor = postDao.getPostsByAuthor(authorId);

        return postsByAuthor;
    }

    public Post getPost(int id) {
        post = postDao.getPost(id);
        return post;
    }

    public void insert(Post post){
        postDao.insert(post);
    }
    public void delete(Post post){
        postDao.delete(post);
    }
   public void update(Post post){
        postDao.update(post);
   }

}
