package com.example.mytaskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.User;


public class SignUpFragment extends Fragment {

    public static final String EXTRA_USERS_SING_UP =
            "com.example.finaltaskmanager.UserSingUp";
    public static final String EXTRA_USERS_M_CURRENT_INDEX =
            "com.example.finaltaskmanager.UsersMCurrentIndex";

    public static final String SIGN_UP_ARGS_CURRENT_INDEX = "SIGN_UP_ARGS_CURRENT_INDEX";

    private EditText mUserName;
    private EditText mPassword;
    private Button mSignUpButton;

    private User[] mUsers = new User[10];
    private User mUser;
    private int mCurrentIndex = 0;


    public static final String SIGN_UP_ARGS_USER = "SignUpArgsUser";
    public static final String SIGN_UP_ARGS_USERS = "SignUpArgsUsers";


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(User user, User[] users, int currentIndex) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putSerializable(SIGN_UP_ARGS_USER, user);
        args.putSerializable(SIGN_UP_ARGS_USERS, users);
        args.putInt(SIGN_UP_ARGS_CURRENT_INDEX, currentIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser = (User) getArguments().getSerializable(SIGN_UP_ARGS_USER);
        mUsers = (User[]) getArguments().getSerializable(SIGN_UP_ARGS_USERS);
        mCurrentIndex = getArguments().getInt(SIGN_UP_ARGS_CURRENT_INDEX);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        setListeners();

        return view;
    }

    private void findViews(View view) {
        mUserName = view.findViewById(R.id.txt_username_second);
        mPassword = view.findViewById(R.id.txt_password_second);
        mSignUpButton = view.findViewById(R.id.btn_sign_up_second);

        mUserName.setText(mUser.getUserName());
        mPassword.setText(mUser.getUserPassword());
    }


    private void setListeners() {
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResult();
            }
        });
    }

    private void sendResult() {

        mUsers[mCurrentIndex++] = new User(
                mUserName.getText().toString(),
                mPassword.getText().toString());

        Intent intent = new Intent();
        intent.putExtra(EXTRA_USERS_SING_UP, mUsers);
        intent.putExtra(EXTRA_USERS_M_CURRENT_INDEX, mCurrentIndex);

        getTargetFragment().
                onActivityResult(LoginFragment.Request_Code_Sign_Up,
                        Activity.RESULT_OK,
                        intent);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(SignUpFragment.this).commit();

    }
}