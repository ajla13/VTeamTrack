package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "team")
public class Team {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String name;

    private boolean publicTeam;

    public boolean isPublicTeam() {
        return publicTeam;
    }

    public void setPublicTeam(boolean publicTeam) {
        this.publicTeam = publicTeam;
    }

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

    public Team(String name, boolean publicTeam){

        this.name=name;
        this.publicTeam = publicTeam;

    }
    public static Team[] populateTeam() {
        return new Team[] {
                new Team( "Coast VT", true),
                new Team( "Leading VT", false
                ),
        };
    }

}
