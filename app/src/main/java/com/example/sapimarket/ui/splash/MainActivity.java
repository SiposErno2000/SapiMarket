package com.example.sapimarket.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.sapimarket.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SplashFragment splashFragment = new SplashFragment();
        loadFragment(splashFragment);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainActivity, fragment).commit();
    }
}