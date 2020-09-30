package com.example.mytaskmanager.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.mytaskmanager.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {


    public static final String ARGS_TASK_DATE = "taskDateArgs";
    public static final String EXTRA_USER_SELECTED_DATE = "com.example.mytaskmanager.userSelectedDate";

    private Date mTaskDate;
    private DatePicker mDatePicker;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    public static DatePickerFragment newInstance(Date Date) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_DATE, Date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskDate = (Date) getArguments().getSerializable(ARGS_TASK_DATE);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(
                R.layout.fragment_date_picker, null, false);

        findViews(view);
        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Select Date")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date userSelectedDate = extractDateFromDatePicker();
                        sendResult(userSelectedDate);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();


        return dialog;
    }

    private void findViews(View view) {
        mDatePicker = view.findViewById(R.id.date_picker_fragment);
    }

    private void initViews() {
        initDatePicker();
    }

    private void initDatePicker() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePicker.init(year, monthOfYear, dayOfMonth, null);
    }

    private Date extractDateFromDatePicker() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int dayOfMonth = mDatePicker.getDayOfMonth();

        GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        return calendar.getTime();
    }

    private void sendResult(Date userSelectedDate) {
        Fragment fragment = getTargetFragment();

        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USER_SELECTED_DATE, userSelectedDate);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}