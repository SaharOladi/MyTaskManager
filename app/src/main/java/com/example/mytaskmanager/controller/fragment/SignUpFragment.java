package com.example.mytaskmanager.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.model.User;
import com.example.mytaskmanager.repository.UserDBRepository;


public class SignUpFragment extends Fragment {

    private static final String ARGS_USERNAME = "args_username";
    private static final String ARGS_PASSWORD = "args_password";

    private User mUser = new User();
    private UserDBRepository mUserRepository;

    private Button mSignUp;
    private EditText mUsername, mPassword;

    public static SignUpFragment newInstance(String username, String password) {
        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        args.putSerializable(ARGS_USERNAME, username);
        args.putSerializable(ARGS_PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUser.setUserName(getArguments().getString(ARGS_USERNAME));
        mUser.setPassword(getArguments().getString(ARGS_PASSWORD));

    }

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        initUI(view);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateUsername())
                    if (validatePassword()) {
                        sendResult();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().remove(SignUpFragment.this).commit();
                    }
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

    }

    private void sendResult() {

        mUser.setUserName(mUsername.getText().toString());
        mUser.setPassword(mPassword.getText().toString());
        mUserRepository.insertUser(mUser);
        Intent intent = new Intent();
        getTargetFragment().onActivityResult(LoginFragment.REQUEST_CODE_SIGN_UP, Activity.RESULT_OK, intent);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(SignUpFragment.this).commit();
    }

    private void initUI(View view) {
        mUsername = view.findViewById(R.id.txt_username_second);
        mPassword = view.findViewById(R.id.txt_password_second);
        mSignUp = view.findViewById(R.id.btn_sign_up_second);
        mUserRepository = UserDBRepository.getInstance(getActivity().getApplicationContext());

        mUsername.setText(mUser.getUserName());
        mPassword.setText(mUser.getPassword());
    }

    private boolean validateUsername() {
        String username = mUsername.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(getActivity(), "You Can Not SignUp, Please Fill UserName", Toast.LENGTH_LONG).show();
            return false;
        } else if (mUserRepository.getUser(username) != null) {
            Toast.makeText(getActivity(), "Username not available, Choose another", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = mPassword.getText().toString().trim();
        if (password.isEmpty()) {
            return false;
        }
        return true;

    }
}