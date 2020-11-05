package com.example.mytaskmanager.controller.activity;

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
import com.example.mytaskmanager.controller.fragment.TaskListFragment;
import com.example.mytaskmanager.model.State;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class PagerActivity extends AppCompatActivity {

    public static final String EXTRA_BUNDLE_USERNAME = "com.example.mytaskmanager.extraBundleUsername";
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    PageAdapter mPageAdapter;
    private String mUserName;

    List<State> mTaskState = new ArrayList<State>() {{
        add(State.TODO);
        add(State.DOING);
        add(State.DONE);
    }};


    public static Intent newIntent(Context context, String userName) {
        Intent intent = new Intent(context, PagerActivity.class);
        intent.putExtra(EXTRA_BUNDLE_USERNAME, userName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        mUserName = getIntent().getStringExtra(EXTRA_BUNDLE_USERNAME);
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

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPageAdapter.mFragmentList.get(position).updateUI(mTaskState.get(position),mUserName);
            }

        });

    }


    private class PageAdapter extends FragmentStateAdapter {

        private List<TaskListFragment> mFragmentList = new ArrayList<TaskListFragment>() {{
            add(TaskListFragment.newInstance(mTaskState.get(0),mUserName));
            add(TaskListFragment.newInstance(mTaskState.get(1),mUserName));
            add(TaskListFragment.newInstance(mTaskState.get(2),mUserName));
        }};

        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return TaskListFragment.newInstance(mTaskState.get(position),mUserName);
        }

        @Override
        public int getItemCount() {
            return 3;
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        mPageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPageAdapter.notifyDataSetChanged();
    }
}
