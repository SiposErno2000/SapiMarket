package com.example.sapimarket.ui.splash;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sapimarket.R;
import com.example.sapimarket.data.ModelCache;
import com.example.sapimarket.ui.login.LoginFragment;

public class SplashFragment extends Fragment {

    private View view;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        startAnimation();
        navigation();
        return view;
    }

    private void startAnimation() {
        Animation topAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.top_animation);
        Animation bottomAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_animation);

        ImageView logo = view.findViewById(R.id.logo);
        TextView appName = view.findViewById(R.id.appName);

        appName.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);

        ModelCache modelCache = ModelCache.getInstance();
        modelCache.loadData();
    }

    private void navigation() {
        new Handler().postDelayed(() -> {
            LoginFragment loginFragment = new LoginFragment();
            loadFragment(loginFragment);
        }, 3000);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.pop_up, FragmentTransaction.TRANSIT_NONE)
                .addToBackStack(null)
                .replace(R.id.mainActivity, fragment)
                .commit();
    }
}