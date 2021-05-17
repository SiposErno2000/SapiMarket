package com.example.sapimarket.ui.detailPages;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.sapimarket.R;
import com.example.sapimarket.data.ModelCache;
import com.example.sapimarket.ui.home.Model;

import java.util.Objects;


public class DetailPageFragment extends Fragment {

    ImageView itemImage,star;
    TextView price, name, telNumber, description, seller;
    AppCompatButton addToCart, callSeller;

    public static final String EXTRA_NAME = "extra_name";

    public DetailPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_detail_page, container, false);
        itemImage = (ImageView) view.findViewById(R.id.item_image);
        price = (TextView) view.findViewById(R.id.price_container);
        name = (TextView) view.findViewById(R.id.item_text);
        telNumber = (TextView) view.findViewById(R.id.phoneNum_container);
        description = (TextView) view.findViewById(R.id.item_description);
        seller = (TextView) view.findViewById(R.id.owner_container);
        addToCart = (AppCompatButton) view.findViewById(R.id.add_to_cart_button);
        callSeller = (AppCompatButton) view.findViewById(R.id.call_seller_button);
        star = (ImageView) view.findViewById(R.id.star);
        Bundle bundle = this.getArguments();
        String nameText = bundle.getString(EXTRA_NAME);
        name.setText(nameText);
        Model model = ModelCache.getInstance().getModel(nameText);
        if (model != null) {
            price.setText(model.getPrice() + " RON");
            telNumber.setText("0747124643");
            description.setText(model.getDescription());
            seller.setText(model.getSeller());
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(model.getImageUrl())
                    .into(itemImage);
        }
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Item added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        callSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telNumber.getText()));
                startActivity(intent);
            }
        });

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                star.setBackgroundColor();
//                star.setBackgroundTintMode();
            }
        });
        return view;
    }
}