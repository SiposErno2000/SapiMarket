package com.example.sapimarket.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sapimarket.R;
import com.example.sapimarket.ui.detailPages.DetailPageFragment;

import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {

    private final Context context;
    private List<Model> modelList;
    public static final String EXTRA_NAME = "extra_name";

    public ModelAdapter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_layout_model, parent, false);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        final Model model = modelList.get(position);
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        holder.category.setText(model.getCategory());
        holder.seller.setText(model.getSeller());

        Glide.with(context).load(model.getImageUrl()).into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_NAME, holder.name.getText().toString());
            DetailPageFragment detailFragment = new DetailPageFragment();
            detailFragment.setArguments(bundle);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.pop_up, FragmentTransaction.TRANSIT_NONE)
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, detailFragment)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, category, seller;
        ImageView image;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.modelImage);
            seller = itemView.findViewById(R.id.modelSeller);
            price = itemView.findViewById(R.id.modelPrice);
            category = itemView.findViewById(R.id.modelCategory);
            name = itemView.findViewById(R.id.modelName);
        }
    }
}
