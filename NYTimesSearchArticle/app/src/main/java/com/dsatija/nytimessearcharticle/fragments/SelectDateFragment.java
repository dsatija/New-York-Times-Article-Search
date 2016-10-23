package com.dsatija.nytimessearcharticle.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Disha on 10/20/2016.
 */
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog.OnDateSetListener settingsChangeListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            settingsChangeListener = (DatePickerDialog.OnDateSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement OnDateSetListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        Bundle b = getArguments();
        if (b != null) {
            if (b.containsKey("beginDateTimestamp")) {
                Long beginDateTimestamp = b.getLong("beginDateTimestamp");
                if (beginDateTimestamp != null) {
                    calendar.setTimeInMillis(beginDateTimestamp);
                }
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        settingsChangeListener.onDateSet(view, yy, mm, dd);
    }
}
