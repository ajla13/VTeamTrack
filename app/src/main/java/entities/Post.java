package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "post")
public class Post {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String content;

    public int likes;

    public int authorId;

    public String teamName;

    public Date date;

    public Post(){}
    public Post(String content, int likes, int authorId, Date date, String teamName){

        this.content=content;
        this.likes=likes;
        this.authorId=authorId;
        this.date=date;
        this.teamName = teamName;
    }
    public static Post[] populatePost() throws ParseException {
        return new Post []{
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT"),
                new Post( "Test post 1", 20, 1, new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT")
        };

    }
}
