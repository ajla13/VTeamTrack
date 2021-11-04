package com.ul.lj.si.vteamtrack.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private EditText editText;
    private int editTextId;
    private int _day;
    private int _month;
    private int _year;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }



    public void onDateSet(DatePicker view, int year, int month, int day) {
        _year = year;
        _month = month;
        _day = day;
        if(this.getArguments().getString("source").equals("editText")){
            editTextId=this.getArguments().getInt("viewId");
            editText = getActivity().findViewById(editTextId);
            editText.setText(new StringBuilder()
                    .append(_day).append("/").append(_month + 1).append("/").append(_year).append(" "));
        }



    }
}