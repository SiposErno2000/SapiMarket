package com.example.sapimarket.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.sapimarket.R;
import com.example.sapimarket.ui.login.LoginFragment;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        overridePendingTransition(R.anim.pop_up, FragmentTransaction.TRANSIT_NONE);

        HomeFragment homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.pop_up, FragmentTransaction.TRANSIT_NONE)
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        List fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for (Object f : fragmentList) {
            if (f instanceof HomeFragment) {
                handled = ((HomeFragment) f).onBackPressed();
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