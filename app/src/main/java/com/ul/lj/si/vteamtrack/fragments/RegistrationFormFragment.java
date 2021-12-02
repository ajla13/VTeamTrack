package com.ul.lj.si.vteamtrack.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ul.lj.si.vteamtrack.CreateTrainingActivity;
import com.ul.lj.si.vteamtrack.Login;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.UploadImage;
import com.ul.lj.si.vteamtrack.helpers.ImageHandler;

import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entities.Fee;
import entities.FeeMonth;
import entities.Team;
import entities.User;

import viewModels.FeeModel;
import viewModels.FeeMonthModel;
import viewModels.TeamModel;
import viewModels.UserModel;

public class RegistrationFormFragment extends Fragment {
    private EditText email;
    private EditText password;
    private EditText passRepeat;
    private EditText teamName;
    private EditText name;
    private EditText phone;
    private EditText surname;
    private EditText dateOfBirth;
    private Team team;
    private String successText;
    private UserModel userModel;
    private TeamModel teamModel;
    private FeeModel feeModel;
    private User user;
    private String errorText;
    private String registrationType;
    private ImageView image;
    private ImageButton imgPlus;
    private ActivityResultLauncher<Intent> launchActivity;
    private Uri imageUri;
    private ImageHandler imageHandler;
    private Bitmap bitmap;
    private FeeMonthModel feeMonthModel;

    public RegistrationFormFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        feeModel = new ViewModelProvider(this).get(FeeModel.class);
        feeMonthModel = new ViewModelProvider(this).get(FeeMonthModel.class);
        registrationType = this.getArguments().getString("registrationType");
        imageUri=null;
        imageHandler = new ImageHandler();
        bitmap=null;

        launchActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            imageUri = data.getData();

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            Glide.with(getActivity().getApplicationContext())
                                    .load(data.getData())
                                    .into(image);
                        }
                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.registration, container, false);
        email=(EditText) view.findViewById(R.id.registration_email);
        password=(EditText) view.findViewById(R.id.registration_password);
        passRepeat=(EditText) view.findViewById(R.id.registration_password_repeat);
        name=(EditText) view.findViewById(R.id.registration_name);
        phone=(EditText) view.findViewById(R.id.registration_phone);
        teamName=(EditText) view.findViewById(R.id.registration_team);
        surname=(EditText) view.findViewById(R.id.registration_surname);
        dateOfBirth=(EditText) view.findViewById(R.id.registration_dateOfBirth);
        image = (ImageView) view.findViewById(R.id.register_image);
        imgPlus = (ImageButton) view.findViewById(R.id.register_img_plus);

        Button register=(Button) view.findViewById(R.id.btn_register);

        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), UploadImage.class);
                intent.putExtra("source", "registration");
                launchActivity.launch(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    if(creditentials()){
                        Date dOfB= new Date();
                        try {
                            dOfB=new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(registrationType=="trainer"){
                            String firstName = name.getText().toString();
                            String lastname =  surname.getText().toString();
                            String teamNameDb = teamName.getText().toString();
                            String emaileDb = email.getText().toString();
                            String pass = password.getText().toString();
                            String phoneDb = phone.getText().toString();
                            team = new Team(teamNameDb, false);
                            String pathToFile = imageHandler.saveToInternalStorage(bitmap, firstName+"-"+lastname+".jpg", getActivity().getApplicationContext());

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();

                            Team teamReturned = teamModel.createTeam(team);
                            Team teamWithId = teamModel.getTeam(teamReturned.getName());
                            User user = new User(imageInByte,teamWithId.getId(),firstName, lastname, dOfB,teamNameDb, emaileDb,pass,
                                    "trainer",phoneDb,true, pathToFile);

                            User createdUser = userModel.createUser(user);
                            Calendar cal = Calendar.getInstance();
                            String currentMonth= new SimpleDateFormat("MMM").format(cal.getTime());

                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyy");

                            Calendar c = Calendar.getInstance();
                            c.add(Calendar.YEAR, 1);
                            String validationDateAsString = dateFormat.format(c.getTime());

                            Date validationDate = new Date();
                            try {
                                validationDate = dateFormat.parse(validationDateAsString);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            FeeMonth feeMonth = new FeeMonth(currentMonth,validationDate, teamWithId.getId(),teamWithId.getName());
                            feeMonthModel.insert(feeMonth);
                            FeeMonth feeMonthReturned = feeMonthModel.getFeeMonthByMonth(currentMonth);
                            List<User> userList = userModel.getUserList();
                            if(userList!=null) {
                                for (User userItem : userList) {
                                    if (userItem.getUserRole().equals("player") || userItem.getUserRole().equals("admin")) {
                                        Fee userFee = new Fee(currentMonth, userItem.getId(), false,
                                                teamWithId.getName(), "10", feeMonthReturned.getId());
                                        feeModel.insert(userFee);
                                    }

                                }
                            }
                            successText="Registration successful";
                        }
                        else {
                            String pathToFile = imageHandler.saveToInternalStorage(bitmap,  name.getText().toString()+"-"+ surname.getText().toString(),getActivity().getApplicationContext());
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            User user = new User(imageInByte,0,
                                    name.getText().toString(),
                                    surname.getText().toString(),
                                    dOfB,teamName.getText().toString(),email.getText().toString(),password.getText().toString(),
                                    "player",phone.getText().toString(),false,pathToFile);

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat monthDate = new SimpleDateFormat("MM");

                            Team teamPlayer = teamModel.getTeam(teamName.getText().toString());
                            user.setTeamId(teamPlayer.getId());

                            userModel.createUser(user);

                            successText="Your registration request has been sent.";
                        }

                        Toast.makeText(getActivity().getApplicationContext(),
                                successText, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                errorText, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return view;
    }

    boolean creditentials(){
        Team team = teamModel.getTeam(teamName.getText().toString());
        if(registrationType.equals("trainer")){

            if(team==null){
                return true;
            }
            errorText="A team under that name is already registered.";
            return false;
        }
        else{
            if(team==null){
                errorText="A team under that name is not registered.";
                return false;

            }
            user = userModel.checkUserCred(email.getText().toString(),teamName.getText().toString());
            if(user == null){
                return true;
            }
            if(user.isRegistrationConfirmed()) {
                errorText = "A player with that email already exists.";
            }
            else {
                errorText="A registration request with that email already exists.";
            }
            return false;
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public boolean dataValidation(){
        boolean returnVaue=true;
        if(isEmpty(teamName)){
            teamName.setError("Name of the team can not be empty");
            returnVaue=false;
        }
        if(isEmpty(password)){
            password.setError("Password can not be empty");
            returnVaue=false;
        }
        if(isEmpty(passRepeat) || !passRepeat.getText().toString().equals(password.getText().toString())){
            passRepeat.setError("Passwords do not match");
            returnVaue=false;
        }
        if(isEmpty(email)){
            email.setError("Email can not be empty");
            returnVaue=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError("Please enter a valid email");
            returnVaue=false;
        }
        if(isEmpty(name)){
            name.setError("Name can not be empty");
            returnVaue=false;
        }
        if(isEmpty(surname)){
            surname.setError("Surname can not be empty");
            returnVaue=false;
        }
        if(isEmpty(phone)){
            phone.setError("Phone number can not be empty");
            returnVaue=false;
        }
        if(isEmpty(dateOfBirth)){
            dateOfBirth.setError("Date of birth can not be empty");
            returnVaue=false;
        }
        return returnVaue;
    }

}
