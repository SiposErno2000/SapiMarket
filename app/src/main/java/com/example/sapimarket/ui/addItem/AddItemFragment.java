package com.example.sapimarket.ui.addItem;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sapimarket.R;

public class AddItemFragment extends Fragment {

    EditText name,price,category,description;
    AppCompatButton addItemButton;
    TextView addPictureButton;

    public AddItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_item, container, false);
        name = view.findViewById(R.id.item_name);
        price = view.findViewById(R.id.item_price);
        category = view.findViewById(R.id.item_category);
        description = view.findViewById(R.id.item_description);
        addItemButton = view.findViewById(R.id.add_item_button);
        addPictureButton  = view.findViewById(R.id.add_picture_button);
        return  view;
    }
}