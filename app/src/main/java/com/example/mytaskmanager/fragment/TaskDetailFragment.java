package com.example.mytaskmanager.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;
import com.example.mytaskmanager.repository.TaskRepository;

import java.util.Calendar;
import java.util.Date;


public class TaskDetailFragment extends DialogFragment {

    public static final String ARGS_TASK_STATE = "tagTaskState";
    public static final String ARGS_TASK = "ARGS_TASK";
    public static final String EXTRA_TASK = "EXTRA_TASK";
    private EditText mTaskTitle, mTaskDescription;
    private Button mTaskDate, mTaskTime;
    private CheckBox mTaskState;

    private Task mTask;
    private TaskRepository mTaskRepository;
    private State mState;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance(Task task, State state) {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK, task);
        args.putSerializable(ARGS_TASK_STATE, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = (Task) getArguments().getSerializable(ARGS_TASK);
        mTask.setTaskState((State) getArguments().getSerializable(ARGS_TASK_STATE));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_task_detail, null);


        findViews(view);
        initViews();
        setListeners();


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResult(mTask);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view);

        return builder.create();

    }



    private void sendResult(Task task){

        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK, task);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    private void findViews(View view){
        mTaskTitle = view.findViewById(R.id.task_edit_text_title);
        mTaskDescription = view.findViewById(R.id.task_edit_text_description);
        mTaskDate = view.findViewById(R.id.task_button_date);
        mTaskTime = view.findViewById(R.id.task_button_time);
        mTaskState = view.findViewById(R.id.task_checkbox_state);
    }

    private void initViews(){
        mTaskDate.setText(mTask.getJustDate());
        mTaskTime.setText(mTask.getJustTime());
        mTaskState.setText(mTask.getTaskState().toString());
    }

    private void setListeners(){
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

        mTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mTaskState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }
}