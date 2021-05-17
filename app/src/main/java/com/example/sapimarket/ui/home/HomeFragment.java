package com.example.sapimarket.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentTransaction;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sapimarket.R;
import com.example.sapimarket.data.ModelCache;

import java.util.ArrayList;
import java.util.List;
import com.example.sapimarket.ui.addItem.AddItemFragment;

public class HomeFragment extends Fragment {

    private View view;
    private List<Model> modelList;
    private ModelAdapter adapter;

    private Button addButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container,new AddItemFragment());
                fragmentTransaction.commit();
            }
        });
        //return view;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getModels();
        return view;
    }

    private void createUIElements() {
        RecyclerView recyclerView = view.findViewById(R.id.homeRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ModelAdapter(modelList, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void getModels() {
        ModelCache modelCache = ModelCache.getInstance();
        modelList = new ArrayList<>();
        if (modelCache.getModelList() != null) {
            modelList = modelCache.getModelList();
            createUIElements();
        } else {
            createUIElements();
            Toast.makeText(getActivity(), "No data to load!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.alert_message);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.positive_alert_button, (dialog, which) -> System.exit(0));
        builder.setNegativeButton(R.string.negative_alert_button, (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return true;
    }
}