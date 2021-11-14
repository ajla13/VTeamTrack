package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;


import entities.Post;

import repository.PostRepo;

public class PostModel extends AndroidViewModel {

    private PostRepo postRepo;
    private LiveData<List<Post>> posts;
    private List<Post> authorPosts;

    public PostModel(@NonNull Application application) {
        super(application);
        postRepo = new PostRepo(application);
        posts = postRepo.getAllPosts();


    }

    public LiveData<List<Post>> getPosts() {
        if (posts == null) {
            posts = postRepo.getAllPosts();
        }
        return posts;
    }
    public List<Post> getPostsByAuthor(int authorId) {
        return postRepo.getPostsByAuthor(authorId);
    }

    public Post getPost(int id){
        return postRepo.getPost(id);
    }

    public Post createPost(Post post){
        postRepo.insert(post);
        return post;
    }

    public void updatePost(Post post){
        postRepo.update(post);
    }
    public void deletePost(Post post){
        postRepo.delete(post);
    }

}