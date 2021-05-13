package com.example.sapimarket.ui.splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.sapimarket.R;
import com.example.sapimarket.ui.login.LoginFragment;
import com.example.sapimarket.ui.register.RegisterFragment;

import java.util.List;

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
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainActivity, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        List fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for (Object f: fragmentList) {
            if (f instanceof LoginFragment) {
                handled = ((LoginFragment)f).onBackPressed();
                if (handled) {
                    break;
                }
            }
            else if (f instanceof RegisterFragment) {
                handled = ((RegisterFragment)f).onBackPressed();
                if (handled) {
                    break;
                }
            }
        }
        if (!handled) {
            super.onBackPressed();
        }
    }
}