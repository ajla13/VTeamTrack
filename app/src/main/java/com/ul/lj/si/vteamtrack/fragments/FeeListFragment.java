package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.FeeAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import entities.Fee;

import viewModels.FeeModel;

import viewModels.UserModel;

public class FeeListFragment extends Fragment {

    private UserModel userModel;
    private FeeModel feeModel;
    private Button january;
    private Button february;
    private Button march;
    private Button april;
    private Button may;
    private Button june;
    private Button july;
    private Button august;
    private Button september;
    private Button october;
    private Button november;
    private Button december;
    private TextView validationDate;
    private ArrayList<Fee> feeList;
    private FeeAdapter feeAdapter;
    RecyclerView rvFee;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.fee_fragment, container, false);
        feeModel=new ViewModelProvider(getActivity()).get(FeeModel.class);
        String userRole = PreferenceData.getUserRole(getActivity().getApplicationContext());
        int currentUser = PreferenceData.getLoggedInUser(getActivity().getApplicationContext());
        if(userRole.equals("trainer")){
            feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Jan");

        }
        else {
            feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Jan");
        }

        validationDate= view.findViewById(R.id.validationDate);

        Date currentDate = new Date();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(yearFormat.format(currentDate)), 0,1);

        Calendar c2 = Calendar.getInstance();
        c2.set(Integer.parseInt(yearFormat.format(currentDate)), 0,1);
        c2.add(Calendar.YEAR, -1);



        if(new Date().compareTo(c.getTime())<0){
            validationDate.setText("Membership for january "+(yearFormat.format(c2.getTime())).toString());
        }
        else{
            validationDate.setText("Membership for january "+(yearFormat.format(c.getTime())).toString());

        }

        january = view.findViewById(R.id.januray);
        january.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Jan");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Jan");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 0,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for january "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for january "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        february = view.findViewById(R.id.february);
        february.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Feb");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Feb");
                }
                setAdapter();
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 1,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for february "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for february "+(yearFormat.format(c.getTime())).toString());

                }
            }
        });
        march = view.findViewById(R.id.march);
        march.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Mar");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Mar");
                }
                setAdapter();
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 2,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for march "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for march "+(yearFormat.format(c.getTime())).toString());

                }
            }
        });
        april = view.findViewById(R.id.april);
        april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Apr");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Apr");
                }
                setAdapter();
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 3,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for april "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for april "+(yearFormat.format(c.getTime())).toString());

                }
            }
        });
        may = view.findViewById(R.id.may);
        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("May");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "May");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 4,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for may "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for may "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        june = view.findViewById(R.id.june);
        june.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Jun");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Jun");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 5,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for june "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for june "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        july = view.findViewById(R.id.july);
        july.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Jul");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Jul");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 6,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for july "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for july "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        august = view.findViewById(R.id.august);
        august.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Aug");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Aug");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 7,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for august "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for august "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        september = view.findViewById(R.id.september);
        september.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Sep");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Sep");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 8,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for september "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for september "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        october = view.findViewById(R.id.october);
        october.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Oct");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Oct");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 9,1);
                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for october "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for october "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        november = view.findViewById(R.id.november);
        november.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Nov");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Nov");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 10,1);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for november "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for november "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });
        december = view.findViewById(R.id.december);
        december.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userRole.equals("trainer")){
                    feeList = (ArrayList<Fee>) feeModel.getFeeByMonth("Dec");

                }
                else {
                    feeList=(ArrayList<Fee>)feeModel.getMonthlyPlayerFee(currentUser, "Dec");
                }
                c.set(Integer.parseInt(yearFormat.format(currentDate)), 11,30);

                if(new Date().compareTo(c.getTime())<0){
                    validationDate.setText("Membership for december "+(yearFormat.format(c2.getTime())).toString());
                }
                else{
                    validationDate.setText("Membership for december "+(yearFormat.format(c.getTime())).toString());

                }
                setAdapter();
            }
        });

        userModel = new ViewModelProvider(this).get(UserModel.class);

        rvFee = (RecyclerView) view.findViewById(R.id.rvFee);


        if (feeList != null){

            feeAdapter = new FeeAdapter(feeList,getActivity());
            rvFee.setAdapter(feeAdapter);
            rvFee.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("gwyd","fee list was null");

            feeAdapter = new FeeAdapter(new ArrayList<Fee>(),getActivity());
            rvFee.setAdapter(feeAdapter);
            rvFee.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return view;
    }

    public void setAdapter(){
        if (feeList != null){

            feeAdapter = new FeeAdapter(feeList,getActivity());
            rvFee.setAdapter(feeAdapter);
            rvFee.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("gwyd","fee list was null");

            feeAdapter = new FeeAdapter(new ArrayList<Fee>(),getActivity());
            rvFee.setAdapter(feeAdapter);
            rvFee.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}
