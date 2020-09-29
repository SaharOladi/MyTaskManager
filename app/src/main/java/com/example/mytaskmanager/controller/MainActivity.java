package com.example.mytaskmanager.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.fragment.LoginFragment;
import com.example.mytaskmanager.fragment.TaskDetailFragment;
import com.example.mytaskmanager.fragment.ToDoFragment;

public class MainActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

//        if (fragment == null) {
//            TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
//            fragmentManager
//                    .beginTransaction()
//                    .add(R.id.fragment_container, taskDetailFragment)
//                    .commit();
//        }

//        if (fragment == null) {
//            ToDoFragment toDoFragment = new ToDoFragment();
//            fragmentManager
//                    .beginTransaction()
//                    .add(R.id.fragment_container, toDoFragment)
//                    .commit();
//        }

        if (fragment == null) {
            LoginFragment loginFragment = new LoginFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, loginFragment)
                    .commit();
        }


    }
}