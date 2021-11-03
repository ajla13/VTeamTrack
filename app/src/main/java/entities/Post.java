package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "post")
public class Post {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String content;

    public int likes;

    public int authorId;

    public String teamName;

    public String date;

    public Post(){}
    public Post( String content, int likes, int authorId, String date, String teamName){

        this.content=content;
        this.likes=likes;
        this.authorId=authorId;
        this.date=date;
        this.teamName = teamName;
    }
    public static Post[] populatePost() {
        return new Post []{
                new Post( "Test post 1", 20, 1, "03/11/2021", "Coast VT"),
                new Post( "Test post 1", 20, 1, "03/11/2021", "Coast VT"),
                new Post( "Test post 1", 20, 1, "03/11/2021", "Coast VT"),
                new Post( "Test post 1", 20, 1, "03/11/2021", "Coast VT"),
                new Post( "Test post 1", 20, 1, "03/11/2021", "Coast VT")
        };

    }
}
