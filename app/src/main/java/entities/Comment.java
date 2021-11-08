package entities;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "comment", foreignKeys ={ @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "authorId",
        onDelete = CASCADE),
        @ForeignKey(entity = Post.class,
                parentColumns = "id",
                childColumns = "postId",
                onDelete = CASCADE)})
public class Comment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String content;

    private int authorId;

    private int postId;

    private Date date;

    public String getContent() {
        return content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Comment(int authorId, int postId, String content, Date date) {
        this.authorId = authorId;
        this.postId = postId;
        this.content = content;
        this.date = date;
    }

    public static Comment[] populateComment() throws ParseException {
        return new Comment[]{
                new Comment(1, 1, "Test post 1", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021")),
                new Comment(1, 1, "Test post 1", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021")),
                new Comment(1, 1, "Test post 1", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021")),
                new Comment(1, 1, "Test post 1", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"))
        };
    }
}
