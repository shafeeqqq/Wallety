package com.example.shaf.wallety;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public int mHourOfDay;
    public int mMinute;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

       // getTimeData();
        final Calendar c = Calendar.getInstance();

        TextView mDateTextView = getActivity().findViewById(R.id.addDate);
        c.set(year, month, dayOfMonth);

        SimpleDateFormat getDate = new SimpleDateFormat("dd MMM, yyyy");
        String dateToDisplay = getDate.format(c.getTime());

        mDateTextView.setText(dateToDisplay);
    }

    private void getTimeData() {

        DialogFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getActivity().getSupportFragmentManager(), "timePicker");

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
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



    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // do something
        }
    }

}
