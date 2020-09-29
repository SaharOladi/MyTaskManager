package com.example.mytaskmanager.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.Task;


public class TaskDetailFragment extends Fragment {

    private EditText mTaskTitle, mTaskDescription;
    private Button mTaskDate, mTaskTime;
    private CheckBox mTaskState;

    private Task mTask;

    public TaskDetailFragment() {
        // Required empty public constructor
    }

    public static TaskDetailFragment newInstance() {
        TaskDetailFragment fragment = new TaskDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = new Task();
        mTask.setTaskTitle("Testing the task");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);

        findViews(view);
        initViews();
        setListeners();

        return view;
    }

    private void findViews(View view){
        mTaskTitle = view.findViewById(R.id.task_edit_text_title);
        mTaskDescription = view.findViewById(R.id.task_edit_text_description);
        mTaskDate = view.findViewById(R.id.task_button_date);
        mTaskTime = view.findViewById(R.id.task_button_time);
        mTaskState = view.findViewById(R.id.task_checkbox_state);
    }

    private void initViews(){
        mTaskTitle.setText(mTask.getTaskTitle());
        mTaskState.setChecked(true);
        mTaskDate.setText(mTask.getJustDate());
        mTaskDate.setEnabled(false);
        mTaskTime.setText(mTask.getJustTime());
        mTaskTime.setEnabled(false);
    }

    private void setListeners(){
        mTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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