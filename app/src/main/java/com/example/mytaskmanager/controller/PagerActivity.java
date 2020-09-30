package com.example.mytaskmanager.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mytaskmanager.R;
import com.example.mytaskmanager.fragment.DoingFragment;
import com.example.mytaskmanager.fragment.DoneFragment;
import com.example.mytaskmanager.fragment.ToDoFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PagerActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    PageAdapter mPageAdapter;


    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, PagerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        findViews();
        initView();
    }

    private void findViews() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
    }

    private void initView() {
        mPageAdapter = new PageAdapter(this);
        mViewPager.setAdapter(mPageAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                (mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setText("TODO");
                                break;
                            }
                            case 1: {
                                tab.setText("DOING");
                                break;
                            }
                            case 2: {
                                tab.setText("DONE");
                                break;
                            }
                        }
                    }
                });

        tabLayoutMediator.attach();

    }

    private class PageAdapter extends FragmentStateAdapter {


        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return ToDoFragment.newInstance();
                case 1:
                    return DoingFragment.newInstance();
                case 2:
                    return DoneFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }

}