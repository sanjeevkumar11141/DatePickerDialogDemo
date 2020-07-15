package com.sanju007.datepickertestsk;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDatePickerFragment extends DialogFragment {

    private MyDatePickerFragment.ISelectedData mListener;
    DatePickerDialog datePickerDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setLayoutMode(1);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        ((ViewGroup) datePickerDialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);

        return datePickerDialog;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
            String formattedDate = formatSelectedDate(date);
            mListener.onSelectedData(formattedDate);
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyDatePickerFragment.ISelectedData) {
            mListener = (MyDatePickerFragment.ISelectedData) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface ISelectedData {
        void onSelectedData(String string);
    }


    private String formatSelectedDate(String value) {

        String expiryDate = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat expiryDateFormat = new SimpleDateFormat("MM/yy");
        SimpleDateFormat sdfmt1 = new SimpleDateFormat("MM/yy");

        try {
            Date date = format.parse(value);
            expiryDate = sdfmt1.format(date);
            //Toast.makeText(getActivity(), "" + expiryDate, Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return expiryDate;
    }
}
