package com.example.mytaskmanager.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;
import com.example.mytaskmanager.repository.TaskRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;


public class DoingFragment extends Fragment {

    public static final String TASK_DETAIL_FRAGMENT = "taskDetailFragment";
    public static final String CHANGE_TASK_FRAGMENT = "changeTaskFragment";


    public static final int REQUEST_CODE_TASK_DETAIL_FRAGMENT = 0;
    public static final int REQUEST_CODE_CHANGE_TASK_FRAGMENT = 4;


    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private FloatingActionButton mAddTask;
    private ImageView mEmptyImage;
    private TextView mEmptyText;

    private Task mTask = new Task();

    public DoingFragment() {
        // Required empty public constructor
    }

    public static DoingFragment newInstance() {
        DoingFragment fragment = new DoingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);
        initViews();
        setListeners();


        return view;
    }

    private void initViews() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TaskRepository taskRepository = TaskRepository.getInstance();
        List<Task> tasks = taskRepository.getTasksList(State.DOING);

        updateUI(tasks);

    }

    private void updateUI(List<Task> tasks) {
        if (tasks.size() != 0) {

            mEmptyImage.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.GONE);

            if (mTaskAdapter == null) {
                mTaskAdapter = new TaskAdapter(tasks);
                mRecyclerView.setAdapter(mTaskAdapter);
            } else {
                mTaskAdapter.setTasks(tasks);
                mTaskAdapter.notifyDataSetChanged();
            }
        } else {
            mEmptyImage.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.VISIBLE);
        }
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.task_list_recyclerView);
        mAddTask = view.findViewById(R.id.add_task_floating);
        mEmptyImage = view.findViewById(R.id.empty_task_image_view);
        mEmptyText = view.findViewById(R.id.empty_image_text);
    }

    private void setListeners() {
        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TaskDetailFragment taskDetailFragment =
                        TaskDetailFragment.newInstance(mTask, /** mTask.getTaskDate(),**/State.DOING);

                taskDetailFragment.setTargetFragment(
                        DoingFragment.this, REQUEST_CODE_TASK_DETAIL_FRAGMENT);

                taskDetailFragment.show(getFragmentManager(), TASK_DETAIL_FRAGMENT);

            }
        });
    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle, mTextViewDate, mTextViewIcon;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.task_item_title);
            mTextViewDate = itemView.findViewById(R.id.task_item_date);
            mTextViewIcon = itemView.findViewById(R.id.task_item_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ChangeTaskFragment changeTaskFragment = ChangeTaskFragment.newInstance(mTask);

                    changeTaskFragment.setTargetFragment(
                            DoingFragment.this, REQUEST_CODE_CHANGE_TASK_FRAGMENT);

                    changeTaskFragment.show(getFragmentManager(), CHANGE_TASK_FRAGMENT);

                }
            });
        }

        public void bindTask(Task task) {

            mTask = task;
            mTextViewTitle.setText(task.getTaskTitle());
            mTextViewDate.setText(task.getJustDate() + " " + task.getJustTime());
            if (task.getTaskTitle().length() != 0)
                mTextViewIcon.setText(task.getTaskTitle().charAt(0) + "");

        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.task_row_list, parent, false);

            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);

        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (/**(resultCode != 1  && requestCode != 2 && resultCode != Activity.RESULT_OK) ||**/data == null)
            return;

        if (requestCode == REQUEST_CODE_TASK_DETAIL_FRAGMENT) {
            Task task =
                    (Task) data.getSerializableExtra(TaskDetailFragment.EXTRA_TASK);

            TaskRepository.getInstance().addTaskDoing(task);
            TaskRepository.getInstance().updateTask(task);
            updateUI(TaskRepository.getInstance().getTasksList(task.getTaskState()));
        }

        if (requestCode == REQUEST_CODE_CHANGE_TASK_FRAGMENT) {

            switch (resultCode) {
                case ChangeTaskFragment.RESULT_CODE_EDIT_TASK:
                    Task task = (Task) data.getSerializableExtra(ChangeTaskFragment.EXTRA_TASK_CHANGE);
                    TaskRepository.getInstance().updateTask(task);
//                    updateUI(TaskRepository.getInstance().getTasks());
                    updateEditUI();
                    break;
                case ChangeTaskFragment.RESULT_CODE_DELETE_TASK:
                    UUID uuid = (UUID) data.getSerializableExtra(ChangeTaskFragment.EXTRA_TASK_CHANGE_DELETE);
                    TaskRepository.getInstance().removeSingleTask(uuid);
                    updateEditUI();
                    break;
                default:
                    break;
            }

        }

    }

    public void updateEditUI() {
        if (mTaskAdapter != null)
            mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateEditUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateEditUI();
    }
}