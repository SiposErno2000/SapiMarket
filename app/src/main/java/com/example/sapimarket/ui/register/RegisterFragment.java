package com.example.sapimarket.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sapimarket.R;
import com.example.sapimarket.ui.constants.Constants;
import com.example.sapimarket.ui.home.HomeActivity;
import com.example.sapimarket.ui.login.LoginFragment;
import com.example.sapimarket.ui.splash.MainActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.sapimarket.ui.constants.Constants.USERS;

public class RegisterFragment extends Fragment {

    private View view;
    private TextInputLayout fullName;
    private TextInputLayout email;
    private TextInputLayout phoneNo;
    private TextInputLayout password;
    private List<String> users;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        createUIElements();
        return view;
    }

    private void createUIElements() {
        fullName = view.findViewById(R.id.fullname2_text_input);
        email = view.findViewById(R.id.email_text_input);
        phoneNo = view.findViewById(R.id.phone_text_input);
        password = view.findViewById(R.id.password2_text_input);

        view.findViewById(R.id.registerButton).setOnClickListener(registerButtonClickListener);
        view.findViewById(R.id.already_text).setOnClickListener(backToLoginClickListener);

        users = existingUsers();
    }

    private List<String> existingUsers() {
        List<String> users = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child(USERS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            users.add(dataSnapshot.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "No data to load!", Toast.LENGTH_SHORT).show();
                    }
                });
        return users;
    }

    private Boolean validateName() {
        String value = fullName.getEditText().getText().toString();
        for (String user : users) {
            if (user.equals(value)) {
                Toast.makeText(getActivity(), "Name is already exist!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (value.isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String value = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";

        if (value.isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!value.matches(emailPattern)) {
            Toast.makeText(getActivity(), "Invalid email!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String value = phoneNo.getEditText().getText().toString();

        if (value.isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            phoneNo.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String value = password.getEditText().getText().toString();
        String passwordValue = "^" +
                "(?=.*[a-zA-Z])" +
                "(?=.*[@#$%^&+=!])" +
                "(?=\\S+$)" +
                ".{4,}" +
                "$";

        if (value.isEmpty()) {
            Toast.makeText(getActivity(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!value.matches(passwordValue)) {
            Toast.makeText(getActivity(), "Password is to weak!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private final View.OnClickListener backToLoginClickListener = v -> {
        LoginFragment loginFragment = new LoginFragment();
        loadFragment(loginFragment);
    };

    private final View.OnClickListener registerButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validateName() ||  !validateEmail() || !validatePhoneNo() || !validatePassword()) {
                return;
            }
            FirebaseDatabase root = FirebaseDatabase.getInstance();
            DatabaseReference reference = root.getReference(USERS);

            String name = fullName.getEditText().getText().toString();
            String mail = email.getEditText().getText().toString();
            String phone = phoneNo.getEditText().getText().toString();
            String pass = password.getEditText().getText().toString();

            User user = new User(name, mail, phone, pass);

            Constants.setCurrentChild(name);
            reference.child(name).setValue(user);

            Intent intent = new Intent(getActivity().getApplication(), HomeActivity.class);
            loadActivity(intent);
        }
    };

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
        LoginFragment loginFragment = new LoginFragment();
        loadFragment(loginFragment);
        return true;
    }
}