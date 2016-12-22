package com.example.pc.zadanie1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText dateFromDatePicker;

    public DateDialog(View view) {
        dateFromDatePicker = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle SavedInstanceState) {

        final Calendar currentDate = Calendar.getInstance();

        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        String date = day + "-" + (month + 1) + "-" + year;
        dateFromDatePicker.setText(date);
    }
}