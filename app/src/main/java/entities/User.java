package entities;

import static androidx.room.ForeignKey.CASCADE;


import android.content.res.AssetManager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

@Entity(tableName = "user", foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "id",
        childColumns = "teamId",
        onDelete = CASCADE))
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String firstName;

    public String lastName;
    public String teamName;
    public String email;

    public String password;
    public String userRole;
    public String phoneNumber;

    public Date dateOfBirth;

    public boolean registrationConfirmed;

    @ColumnInfo(name = "teamId")
    public int teamId;

    private String imageUri;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public String getImageUri() {
        return imageUri;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getUserRole() {
        return userRole;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public int getTeamId() {
        return teamId;
    }

    public boolean isRegistrationConfirmed() {
        return registrationConfirmed;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setPassword(String password) {
        this.password =  BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setRegistrationConfirmed(boolean registrationConfirmed) {
        this.registrationConfirmed = registrationConfirmed;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
    public User(){}
    public User( byte[] imageInByte, int teamId, String firstName, String lastName, Date dateOfBirth, String teamName, String email, String password, String userRole, String phoneNumber, boolean registrationConfirmed, String imageUri) {

        this.image = imageInByte;
        this.teamId = teamId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.registrationConfirmed = registrationConfirmed;
        this.imageUri = imageUri;

    }
    public static User[] populateData() throws ParseException {

        return new User[] {
                new User(null,1,"Jane", "Doe", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT", "jane.doe@mail.com", "janedoe", "player", "00386725643", true, ""),
                new User(null,1,"John", "Doe", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT","john.doe@mail.com", "johndoe", "player", "00386526781", true, "" ),
                new User(null,1,"Emily", "Hart", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "emily.hart@mail.com", "emilyhart", "player", "00389782826", true,"" ),
                new User(null,1,"Leona", "Martin", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT","leona.martin@mail.com", "leonamartin", "player", "00386543123", true,"" ),
                new User(null,1,"Marin", "Samson",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "marin.samson@mail.com", "marinsamson", "player", "00389724563", true,"" ),
                new User(null,1,"Kendall", "Murphy",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT","kendall.murphy@mail.com", "kendallmurphy", "admin", "00389825634", true,"" ),
                new User(null,1,"Samuel", "Philips",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), "Coast VT","samuel.philips@mail.com", "samuelphilips", "admin", "00389176254", true,""),
                new User(null,1,"Daniel", "Fanton", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT","daniel.fanton@mail.com", "danielfanton", "player", "00389165245", false,"" ),
                new User(null,1,"Mirel", "Jackobs",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "mirel.jackbos@mail.com", "mireljackobs", "player", "00389172564", false,"" ),
                new User(null,1,"Dana", "North",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT","dana.north@mail.com", "dananorth", "player", "00389726561", false,""),
                new User(null,1,"Elsa", "Clarity",new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"),"Coast VT", "elsa.clarity@mail.com", "elsaclarity", "trainer", "00387123678", true,"" ),

        };
    }
}
