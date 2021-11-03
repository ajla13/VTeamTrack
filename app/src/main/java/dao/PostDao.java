package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


import entities.Post;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post WHERE authorId LIKE :authorId")
    List<Post> getPostsByAuthor(int authorId);

    @Query("SELECT * FROM post WHERE id LIKE :postId")
    Post getPost(int postId);

    @Query("SELECT * FROM post WHERE teamName LIKE :teamName")
    LiveData<List<Post>> getAllPosts(String teamName);

    @Insert
    void insert(Post post);

    @Insert
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Post post);
}
