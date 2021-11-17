package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "survey", foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "id",
        childColumns = "teamId",
        onDelete = CASCADE))
public class Survey {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int teamId;

    private String content;

    private String teamName;

    private int customIdentifier;

    public int getCustomIdentifier() {
        return customIdentifier;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCustomIdentifier(int customIdentifier) {
        this.customIdentifier = customIdentifier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public Survey(int teamId, int customIdentifier, String teamName, String content){
        this.teamId=teamId;
        this.customIdentifier=customIdentifier;
        this.content=content;
        this.teamName=teamName;
    }
    public static Survey[] populateSurvey() {
        return new Survey[] {
                new Survey( 1, 0, "Coast VT", "Colour of uniforms:"),
                new Survey( 1, 0, "Coast VT", "Training time:"),
                new Survey( 1, 0,"Coast VT", "What day should the game take place?")

        };
    }
}
