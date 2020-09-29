package com.example.mytaskmanager.fragment;

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

public class LoginFragment extends Fragment {

    public static final int Request_Code_Sign_Up = 0;


    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginButton;
    private Button mSignUpButton;

    private User[] mUsers = new User[10];
    private int mCurrentIndex = 0;


    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListener();


        return view;
    }


    private void findViews(View view) {
        mUserName = view.findViewById(R.id.txt_username);
        mPassword = view.findViewById(R.id.txt_password);
        mLoginButton = view.findViewById(R.id.btn_login);
        mSignUpButton = view.findViewById(R.id.btn_sign_up);
    }

    private void setListener() {
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();

                if (mUserName.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            "You Should Fill The UserName Field and UserPassword Field",
                            Toast.LENGTH_LONG).show();
                }

                if (!mUserName.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
                    user.setUserName(mUserName.getText().toString());
                    user.setUserPassword(mPassword.getText().toString());
//                    mUsers[mCurrentIndex] = user;

                    SignUpFragment signUpFragment = SignUpFragment.
                            newInstance(user, mUsers, mCurrentIndex);

                    signUpFragment.setTargetFragment(
                            LoginFragment.this,
                            Request_Code_Sign_Up);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.
                            beginTransaction().
                            add(R.id.fragment_container, signUpFragment).
                            commit();
                }

            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLogIn = false;
                User user = new User();

                if (mUserName.getText().toString().isEmpty() || mPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            "You Should Fill The UserName Field and UserPassword Field",
                            Toast.LENGTH_LONG).show();
                } else {
                    out:
                    for (int i = 0; i < mCurrentIndex; i++) {
                        if (mUsers[i].getUserName().equals(mUserName.getText().toString()) &&
                                mUsers[i].getUserPassword().equals(mPassword.getText().toString())) {
                            user = mUsers[i];
                            isLogIn = true;
                            break out;
                        }
                    }
                    if (!isLogIn) {
                        Toast.makeText(getActivity(),
                                "You Should Sign Up First",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        ToDoFragment toDoFragment = ToDoFragment.
                                newInstance();

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.
                                beginTransaction().
                                replace(R.id.fragment_container, toDoFragment).
                                commit();

//                        Intent intent = TaskPagerActivity.newIntent(getActivity(), user);
//                        startActivity(intent);
                    }

                }
            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED || data == null)
            return;

        if (requestCode == Request_Code_Sign_Up) {

            mUsers = (User[]) data.getSerializableExtra(SignUpFragment.EXTRA_USERS_SING_UP);
            mCurrentIndex = data.
                    getIntExtra(SignUpFragment.EXTRA_USERS_M_CURRENT_INDEX,
                            0);

            mUserName.setText(mUsers[mCurrentIndex - 1].getUserName());
            mPassword.setText(mUsers[mCurrentIndex - 1].getUserPassword());

        }
    }
}