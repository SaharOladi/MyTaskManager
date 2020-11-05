package com.example.mytaskmanager.controller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.User;
import com.example.mytaskmanager.repository.ITaskRepository;
import com.example.mytaskmanager.repository.TaskDBRepository;
import com.example.mytaskmanager.repository.UserDBRepository;

import java.util.List;

public class AdminFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private UserDBRepository mRepository;
    private ITaskRepository mTaskRepository;
    private AdminAdapter mAdminAdapter;


    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = UserDBRepository.getInstance(getActivity());
        mTaskRepository = TaskDBRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        findViews(view);
        initViews();


        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.user_list_recyclerView);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
    }

    private void updateUI() {
        List<User> userList = mRepository.getUsers();

        if (mAdminAdapter == null) {
            mAdminAdapter = new AdminAdapter(userList);
            mRecyclerView.setAdapter(mAdminAdapter);
        } else {
            mAdminAdapter.setUsers(userList);
            mAdminAdapter.notifyDataSetChanged();
        }
    }

    private class AdminHolder extends RecyclerView.ViewHolder {

        private TextView mUserName, mUserDate, mUserTaskCount;
        private ImageView mDeleteUser;

        private User mUser;

        public AdminHolder(@NonNull View itemView) {
            super(itemView);
            findHolderViews(itemView);
            setListeners();

        }

        private void setListeners() {

            mDeleteUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Do You Want to Delete User?")
                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mRepository.deleteUser(mUser);
                                    mTaskRepository.removeTasksUser(mUser.getUserName());
                                    updateUI();
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.no), null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        private void findHolderViews(@NonNull View itemView) {
            mUserName = itemView.findViewById(R.id.user_name_item);
            mUserDate = itemView.findViewById(R.id.user_date_item);
            mUserTaskCount = itemView.findViewById(R.id.user_task_count);
            mDeleteUser = itemView.findViewById(R.id.user_item_remove);
        }

        private void bindUser(User user) {
            mUser = user;
            mUserName.setText(user.getUserName());
            mUserDate.setText(user.getDate().toString());
            mUserTaskCount.setText(mTaskRepository.getCount(user.getUserName()).toString());
        }
    }


    private class AdminAdapter extends RecyclerView.Adapter<AdminHolder> {

        private List<User> mUsers;

        public List<User> getUsers() {
            return mUsers;
        }

        public void setUsers(List<User> users) {
            mUsers = users;
            notifyDataSetChanged();
        }

        public AdminAdapter(List<User> users) {
            mUsers = users;
        }

        @NonNull
        @Override
        public AdminHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.user_row_list, parent, false);

            AdminHolder adminHolder = new AdminHolder(view);
            return adminHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull AdminHolder holder, int position) {
            User user = mUsers.get(position);
            holder.bindUser(user);
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();


    }

    @Override
    public void onPause() {
        super.onPause();
        updateUI();

    }
}