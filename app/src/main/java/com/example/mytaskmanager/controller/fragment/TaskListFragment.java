package com.example.mytaskmanager.controller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.controller.activity.MainActivity;
import com.example.mytaskmanager.model.State;
import com.example.mytaskmanager.model.Task;
import com.example.mytaskmanager.repository.TaskDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TaskListFragment extends Fragment {

    public static final String TASK_DETAIL_FRAGMENT = "taskDetailFragment";
    public static final String CHANGE_TASK_FRAGMENT = "changeTaskFragment";
    public static final String ARGS_STATE_FROM_PAGER_ACTIVITY = "STATE_FROM_PAGER_ACTIVITY";


    public static final int REQUEST_CODE_TASK_DETAIL_FRAGMENT = 10;
    public static final int REQUEST_CODE_CHANGE_TASK_FRAGMENT = 20;
    public static final int RESULT_CODE_DELETE_TASK_SECOND = 100;
    public static final String EXTRA_TASK_DELETE_SECOND = "com.example.mytaskmanager.EXTRA_TASK_DELETE_SECOND";


    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private FloatingActionButton mAddTask;
    private ImageView mEmptyImage, mDeleteTask, mEditTask;
    private TextView mEmptyText;

    private Task mTask = new Task();
    private State mState;
    private List<Task> mTaskList = new ArrayList<>();
    private TaskDBRepository mTaskDBRepository;


    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(State state) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_STATE_FROM_PAGER_ACTIVITY, state);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);

        mState = (State) getArguments().getSerializable(ARGS_STATE_FROM_PAGER_ACTIVITY);

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
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        mTaskList = mTaskDBRepository.getTasksList(mState);
        mTaskAdapter = new TaskAdapter(mTaskList);
        mRecyclerView.setAdapter(mTaskAdapter);

        if (mTaskList != null)
            updateUI(mState);
    }

    public void updateUI(State state) {


        if (mTaskDBRepository != null) {

            mTaskList = mTaskDBRepository.getTasksList(state);
            if (mTaskList != null || mTaskList.size() != 0) {
                mEmptyImage.setVisibility(View.GONE);
                mEmptyText.setVisibility(View.GONE);
                if (isAdded()) {
                    if (mTaskAdapter != null) {
                        mTaskAdapter.setTasks(mTaskList);
                        mTaskAdapter.notifyDataSetChanged();
                    } else {
                        mTaskAdapter = new TaskAdapter(mTaskList);
                        mRecyclerView.setAdapter(mTaskAdapter);
                    }

                }
                if (mTaskList == null || mTaskList.size() == 0) {
                    mEmptyImage.setVisibility(View.VISIBLE);
                    mEmptyText.setVisibility(View.VISIBLE);
                }
            }
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
                        TaskDetailFragment.newInstance(mTask, mState);

                taskDetailFragment.setTargetFragment(
                        TaskListFragment.this, REQUEST_CODE_TASK_DETAIL_FRAGMENT);

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
            mDeleteTask = itemView.findViewById(R.id.task_item_remove);
            mEditTask = itemView.findViewById(R.id.task_item_edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ChangeTaskFragment changeTaskFragment = ChangeTaskFragment.newInstance(mTask);

                    changeTaskFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_CHANGE_TASK_FRAGMENT);

                    changeTaskFragment.show(getFragmentManager(), CHANGE_TASK_FRAGMENT);

                }
            });

            mDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Do You Want to Delete Task?!")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    State state = mTask.getTaskState();
                                    mTaskDBRepository.removeSingleTask(mTask);
                                    updateUI(state);
                                }
                            })
                            .setNegativeButton("No", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            mEditTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ChangeTaskFragment changeTaskFragment = ChangeTaskFragment.newInstance(mTask);

                    changeTaskFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_CHANGE_TASK_FRAGMENT);

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

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> implements Filterable {

        private List<Task> mTasks;
        private List<Task> mTasksSearch;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
            if (tasks != null)
                mTasksSearch = new ArrayList<>(tasks);
            notifyDataSetChanged();
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
            if (mTasks != null)
                return mTasks.size();
            return 0;
        }

        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    List<Task> filteredList = new ArrayList<>();

                    if (charSequence.toString().isEmpty()) {
                        filteredList.addAll(mTasksSearch);
                    } else {
                        for (Task task : mTasksSearch) {
                            if (task.getTaskTitle().toLowerCase().trim()
                                    .contains(charSequence.toString().toLowerCase().trim()) ||
                                    task.getTaskDescription().toLowerCase().trim()
                                            .contains(charSequence.toString().toLowerCase().trim()) ||
                                    task.getTaskState().toString().trim()
                                            .contains(charSequence.toString().toLowerCase().trim()) ||
                                    task.getJustDate().toLowerCase().trim()
                                            .contains(charSequence.toString().toLowerCase().trim()) ||
                                    task.getJustTime().toLowerCase().trim()
                                            .contains(charSequence.toString().toLowerCase().trim())) {
                                filteredList.add(task);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (mTasks != null)
                        mTasks.clear();
                    if (filterResults.values != null)
                        mTasks.addAll((Collection<? extends Task>) filterResults.values);
                    notifyDataSetChanged();
                }
            };
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;

        if (requestCode == REQUEST_CODE_TASK_DETAIL_FRAGMENT) {
            Task task =
                    (Task) data.getSerializableExtra(TaskDetailFragment.EXTRA_TASK);

            mTaskDBRepository.insertTask(task);
            updateUI(mState);
        }

        if (requestCode == REQUEST_CODE_CHANGE_TASK_FRAGMENT) {
            Task task;
            switch (resultCode) {
                case ChangeTaskFragment.RESULT_CODE_EDIT_TASK:
                    task = (Task) data.getSerializableExtra(ChangeTaskFragment.EXTRA_TASK_CHANGE);
                    mTaskDBRepository.updateTask(task);
                    updateUI(task.getTaskState());
                    break;
                case ChangeTaskFragment.RESULT_CODE_DELETE_TASK:
                    task = (Task) data.getSerializableExtra(ChangeTaskFragment.EXTRA_TASK_DELETE);
                    State state = task.getTaskState();
                    mTaskDBRepository.removeSingleTask(task);
                    updateUI(state);
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull final MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search_task);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mTaskAdapter.getFilter().filter(newText);
                return false;
            }
        });


        MenuItem logout = menu.findItem(R.id.menu_logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Do You Want To Exit?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                getActivity().startActivity(MainActivity.newIntent(getActivity()));

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        MenuItem deleteAll = menu.findItem(R.id.menu_remove_all);
        deleteAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Do You Want To Delete All Tasks?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTaskDBRepository.removeTasks();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

    }

}