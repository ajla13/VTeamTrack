package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "team")
public class Team {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team(String name){

        this.name=name;

    }
    public static Team[] populateTeam() {
        return new Team[] {
                new Team( "Coast VT"),
                new Team( "Leading VT"),
        };
    }

}
