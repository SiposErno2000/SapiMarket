package com.example.sapimarket.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sapimarket.R;
import com.example.sapimarket.ui.constants.Constants;
import com.example.sapimarket.ui.home.HomeActivity;
import com.example.sapimarket.ui.register.RegisterFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.sapimarket.ui.constants.Constants.USERS;

public class LoginFragment extends Fragment {

    private final static String TAG = LoginFragment.class.getSimpleName();
    private View view;
    private TextInputLayout fullname;
    private TextInputLayout password;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        createUIElements();
        return view;
    }

    private void createUIElements() {
        fullname = view.findViewById(R.id.fullname_text_input);
        password = view.findViewById(R.id.password_text_input);

        view.findViewById(R.id.validateButton).setOnClickListener(loginButtonClickListener);
        view.findViewById(R.id.signup_text).setOnClickListener(signUpButtonClickListener);
    }

    private final View.OnClickListener signUpButtonClickListener = v -> {
        RegisterFragment registerFragment = new RegisterFragment();
        loadFragment(registerFragment);
    };

    private final View.OnClickListener loginButtonClickListener = v -> loginUser();

    private void loginUser() {
        String name;
        String pass;

        name = fullname.getEditText().getText().toString();
        pass = password.getEditText().getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getActivity(), "Please enter the name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getActivity(), "Please enter the password!", Toast.LENGTH_SHORT).show();
            return;
        }

        //getData from Firebase
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference(USERS).child(name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    String currentName = snapshot.child("fullname").getValue().toString();
                    String currentPassword = snapshot.child("password").getValue().toString();

                    if (name.equals(currentName) && pass.equals(currentPassword)) {
                        Constants.setCurrentChild(currentName);
                        Intent intent = new Intent(getActivity().getApplication(), HomeActivity.class);
                        loadActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Wrong credentials!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Unsuccessful data request!");
            }
        });
    }

    private void loadActivity(Intent intent) {
        startActivity(intent);
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

    public boolean onBackPressed() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setMessage(R.string.alert_message);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.positive_alert_button, (dialog, which) -> System.exit(0));
        builder.setNegativeButton(R.string.negative_alert_button, (dialog, which) -> dialog.cancel());
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
}