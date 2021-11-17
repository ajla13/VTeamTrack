package entities;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.typeConverters.Converters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "answer", foreignKeys = @ForeignKey(entity = Survey.class,
        parentColumns = "id",
        childColumns = "surveyId",
        onDelete = CASCADE))
public class Answer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int surveyId;

    private String content;

    @TypeConverters(Converters.class)
    private List<Integer> votes;

    public List<Integer> getVotes() {
        return votes;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public void setVotes(List<Integer> votes) {
        this.votes = votes;
    }

    public Answer(int surveyId, String content, List<Integer> votes){
        this.content=content;
        this.surveyId=surveyId;
        this.votes=votes;
    }
    public static Answer[] populateAnswer() {
        return new Answer[] {
                new Answer( 1, "black",new ArrayList<Integer>()),
                new Answer( 1, "red",new ArrayList<Integer>()),
                new Answer( 1, "blue",new ArrayList<Integer>()),
                new Answer( 3, "Monday",new ArrayList<Integer>()),
                new Answer( 3, "Tuesday",new ArrayList<Integer>()),
                new Answer( 2, "11:00",new ArrayList<Integer>()),
                new Answer( 2, "12:00",new ArrayList<Integer>()),
                new Answer( 2, "15:00",new ArrayList<Integer>()),

        };
    }

}
