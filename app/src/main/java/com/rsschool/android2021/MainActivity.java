package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements
        FirstFragment.ButtonClickedListener, SecondFragment.ButtonBackClickedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    private void openSecondFragment(int min, int max) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SecondFragment.newInstance(min, max)).commit();
    }

    @Override
    public void onGenerateButtonClicked(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackButtonClicked(int result) {
        openFirstFragment(result);
    }
}
