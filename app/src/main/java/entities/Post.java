package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "post", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "authorId",
        onDelete = CASCADE))
public class Post {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String content;

    private int likes;

    private int authorId;

    private String teamName;

    private Date date;

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public Date getDate() {
        return date;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getLikes() {
        return likes;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Post(String content, int likes, int authorId, Date date, String teamName){

        this.content=content;
        this.likes=likes;
        this.authorId=authorId;
        this.date=date;
        this.teamName = teamName;
    }
    public static Post[] populatePost() throws ParseException {
        return new Post []{
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),
                        "Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT")
        };

    }
}
