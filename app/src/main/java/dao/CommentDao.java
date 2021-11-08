package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Comment;
import entities.Team;


@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment WHERE postId LIKE :postId")
    LiveData<List<Comment>> getCommentsByPost(int postId);

    @Query("SELECT * FROM comment WHERE id LIKE :commentId")
    Comment getComment(int commentId);

    @Insert
    void insert(Comment comment);

    @Insert
    void insertAll(Comment... comments);

    @Delete
    void delete(Comment comment);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Comment comment);
}
