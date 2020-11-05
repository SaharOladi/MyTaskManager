package com.example.mytaskmanager.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;
import com.example.mytaskmanager.repository.TaskDBRepository;

import java.util.Date;


public class ChangeTaskFragment extends DialogFragment {

    public static final String ARGS_TASK_CHANGE = "ARGS_TASK_CHANGE";

    public static final int REQUEST_CODE_DATE_PICKER_CHANGE_TASK = 30;
    public static final int REQUEST_CODE_TIME_PICKER_CHANGE_TASK = 40;

    public static final String TAG_DATE_PICKER_FRAGMENT_CHANGE_TASK = "TAG_DATE_PICKER_FRAGMENT";
    public static final String TAG_TIME_PICKER_FRAGMENT_CHANGE_TASK = "TAG_TIME_PICKER_FRAGMENT";

    public static final String EXTRA_TASK_CHANGE = "com.example.mytaskmanager.EXTRA_TASK_CHANGE";
    public static final String EXTRA_TASK_DELETE = "com.example.mytaskmanager.EXTRA_TASK_CHANGE_DELETE";

    public static final int RESULT_CODE_EDIT_TASK = 50;
    public static final int RESULT_CODE_DELETE_TASK = 60;

    private EditText mTaskTitle, mTaskDescription;
    private Button mTaskDate, mTaskTime;
    private RadioButton mRadioButtonToDo, mRadioButtonDone, mRadioButtonDoing;
    private RadioGroup mRadioGroupState;

    private Task mTask;


    public ChangeTaskFragment() {
        // Required empty public constructor
    }

    public static ChangeTaskFragment newInstance(Task task) {
        ChangeTaskFragment fragment = new ChangeTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_CHANGE, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = (Task) getArguments().getSerializable(ARGS_TASK_CHANGE);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_change_task, null);

        findViews(view);
        initViews();
        setListeners();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())

                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResultForEdit(mTask);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view);

        return builder.create();
    }

    private void initViews() {
        mTaskTitle.setText(mTask.getTaskTitle());
        mTaskDescription.setText(mTask.getTaskDescription());
        mTaskDate.setText(mTask.getJustDate());
        mTaskTime.setText(mTask.getJustTime());
    }

    private void findViews(View view) {
        mTaskTitle = view.findViewById(R.id.change_task_title);
        mTaskDescription = view.findViewById(R.id.change_task_description);
        mTaskDate = view.findViewById(R.id.change_task_date);
        mTaskTime = view.findViewById(R.id.change_task_time);
        mRadioGroupState = view.findViewById(R.id.radioGroup_taskState);
        mRadioButtonToDo = view.findViewById(R.id.radioButton_todo);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_doing);
        mRadioButtonDone = view.findViewById(R.id.radioButton_done);
    }

    private void setListeners() {
        mTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setTaskTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setTaskDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mTask.getTaskDate());

                datePickerFragment.setTargetFragment(
                        ChangeTaskFragment.this, REQUEST_CODE_DATE_PICKER_CHANGE_TASK);

                datePickerFragment.show(
                        getActivity().getSupportFragmentManager(), TAG_DATE_PICKER_FRAGMENT_CHANGE_TASK);


            }
        });

        mTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mTask.getTaskDate());

                timePickerFragment.setTargetFragment(
                        ChangeTaskFragment.this, REQUEST_CODE_TIME_PICKER_CHANGE_TASK);

                timePickerFragment.show(
                        getActivity().getSupportFragmentManager(), TAG_TIME_PICKER_FRAGMENT_CHANGE_TASK);
            }
        });

        mRadioGroupState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                State state = mTask.getTaskState();
                String userName = mTask.getUserName();

                if (checkedId == mRadioButtonToDo.getId()) {
                    mRadioButtonToDo.setChecked(true);

                    mTask.setTaskState(State.TODO);
                    TaskDBRepository.getInstance(getActivity()).updateTask(mTask);

                } else if (checkedId == mRadioButtonDoing.getId()) {
                    mRadioButtonDoing.setChecked(true);
                    mTask.setTaskState(State.DOING);
                    TaskDBRepository.getInstance(getActivity()).updateTask(mTask);

                } else if (checkedId == mRadioButtonDone.getId()) {
                    mRadioButtonDone.setChecked(true);
                    mTask.setTaskState(State.DONE);
                    TaskDBRepository.getInstance(getActivity()).updateTask(mTask);

                }

                updateEditUI(state,userName);


            }
        });
    }


    private void updateEditUI(State state, String userName) {
        if (getTargetFragment() instanceof TaskListFragment) {
            ((TaskListFragment) getTargetFragment()).updateUI(state, userName);
        }
    }


    private void updateTaskDate(Date userSelectedDate) {

        mTask.setTaskDate(userSelectedDate);
        mTaskDate.setText(mTask.getJustDate());
    }

    private void updateTaskTime(Date userSelectedTime) {

        mTask.setTaskTime(userSelectedTime);
        mTaskTime.setText(mTask.getJustTime());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER_CHANGE_TASK) {
            Date userSelectedDate =
                    (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            updateTaskDate(userSelectedDate);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER_CHANGE_TASK) {
            Date userSelectedTime =
                    (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);

            updateTaskTime(userSelectedTime);
        }
    }

    private void sendResultForEdit(Task task) {

        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_EDIT_TASK;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_CHANGE, task);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    private void sendResultForDelete(Task task) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_DELETE_TASK;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_DELETE, task);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}