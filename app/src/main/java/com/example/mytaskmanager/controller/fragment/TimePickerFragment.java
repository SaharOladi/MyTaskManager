package com.example.mytaskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.mytaskmanager.R;

import java.util.Calendar;
import java.util.Date;


public class TimePickerFragment extends DialogFragment {

    public static final String ARGS_TASK_TIME = "taskTimeArgs";
    public static final String EXTRA_USER_SELECTED_TIME = "com.example.mytaskmanager.EXTRA_USER_SELECTED_TIME";


    private Date mTaskTime;
    private TimePicker mTimePicker;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Date date) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_TIME, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskTime = (Date) getArguments().getSerializable(ARGS_TASK_TIME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view =
                layoutInflater.inflate(R.layout.fragment_time_picker, null, false);

        findViews(view);
        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Select Time")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date userSelectedTime = extractTimeFromTimePicker();
                        sendResult(userSelectedTime);

                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        AlertDialog dialog = builder.create();

        return dialog;


    }

    private void findViews(View view) {
        mTimePicker = view.findViewById(R.id.time_picker_fragment);
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTaskTime);

        if (Build.VERSION.SDK_INT < 23) {
            mTimePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        } else {
            mTimePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            mTimePicker.setMinute(calendar.get(Calendar.MINUTE));
        }
    }

    private Date extractTimeFromTimePicker() {
        int selectedHour = mTimePicker.getCurrentHour();
        int selectedMinute = mTimePicker.getCurrentMinute();

        Calendar currentTime = Calendar.getInstance();

        currentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
        currentTime.set(Calendar.MINUTE, selectedMinute);

        return currentTime.getTime();

    }

    private void sendResult(Date userSelectedTime) {
        Fragment fragment = getTargetFragment();

        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();

        intent.putExtra(EXTRA_USER_SELECTED_TIME, userSelectedTime);

        fragment.onActivityResult(requestCode, resultCode, intent);

    }
}