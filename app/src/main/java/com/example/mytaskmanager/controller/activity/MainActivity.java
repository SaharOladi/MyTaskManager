package com.example.mytaskmanager.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.controller.fragment.LoginFragment;


public class MainActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);


        if (fragment == null) {
            LoginFragment loginFragment = LoginFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, loginFragment)
                    .commit();
        }

    }
}