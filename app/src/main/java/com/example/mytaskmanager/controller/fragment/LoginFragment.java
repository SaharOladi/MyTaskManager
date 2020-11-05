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
import com.example.mytaskmanager.controller.activity.PagerActivity;
import com.example.mytaskmanager.model.User;
import com.example.mytaskmanager.repository.UserDBRepository;


public class LoginFragment extends Fragment {

    static final int REQUEST_CODE_SIGN_UP = 0;
    private static final String TAG_SIGN_UP = "signUpFragment";

    private UserDBRepository mUserRepository;
    private User mUser;
    private EditText mUsername, mUserPassword;
    private Button mLogin, mSingUp;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initUI(view);

        mSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString();
                String password = mUserPassword.getText().toString();
                SignUpFragment signUpFragment = SignUpFragment.newInstance(username, password);
                signUpFragment.setTargetFragment(LoginFragment.this, REQUEST_CODE_SIGN_UP);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.fragment_container, signUpFragment, TAG_SIGN_UP).commit();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsername.getText().toString().trim();
                if (username.equals("admin") && mUserPassword.getText().toString().trim().equals("12345")) {
                    AdminFragment adminFragment= new AdminFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, adminFragment)
                            .addToBackStack(null)
                            .commit();

                } else if (validateUsername())
                    if (validatePassword()) {
                        Intent intent = PagerActivity.newIntent(getActivity(), username);
                        startActivity(intent);
                        getActivity().finish();
                    }
            }
        });

        return view;
    }

    private void initUI(View view) {
        mUserPassword = view.findViewById(R.id.txt_password);
        mUsername = view.findViewById(R.id.txt_username);
        mLogin = view.findViewById(R.id.btn_login);
        mSingUp = view.findViewById(R.id.btn_sign_up);
        mUserRepository = UserDBRepository.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_CANCELED || data == null)
            return;

        if (requestCode == REQUEST_CODE_SIGN_UP) {

            User user = new User();

            mUsername.setText(user.getUserName());
            mUserPassword.setText(user.getPassword());
        }
    }

    private boolean validateUsername() {
        String username = mUsername.getText().toString().trim();
        if (username.isEmpty()) {
            Toast.makeText(getActivity(), "You Can Not LogIn, Please Fill UserName", Toast.LENGTH_LONG).show();
            return false;
        } else if (mUserRepository.getUser(username) == null) {
            Toast.makeText(getActivity(), "You Should Sign Up First!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private Boolean validatePassword() {

        mUser = mUserRepository.getUser(mUsername.getText().toString().trim());
        String password = mUserPassword.getText().toString().trim();


        if (password.isEmpty()) {
            Toast.makeText(getActivity(), "You Can Not LogIn, Please Fill UserPassword", Toast.LENGTH_LONG).show();
            return false;

        } else if (!mUser.getPassword().equals(password)) {
            Toast.makeText(getActivity(), "You Can Not LogIn, Password Does Not Match", Toast.LENGTH_LONG).show();
            return false;

        } else if (mUser.getPassword().equals(password)) {
            return true;
        }
        return null;
    }
}

