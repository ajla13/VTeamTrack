package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "team")
public class Team {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public int userId;

    public Team(){

    }
    public Team(  String name, int userId){

        this.name=name;
        this.userId=userId;
    }
    public static Team[] populateTeam() {
        return new Team[] {
                new Team( "Coast VT", 10),
                new Team( "Leading VT", 11),
        };
    }

}
