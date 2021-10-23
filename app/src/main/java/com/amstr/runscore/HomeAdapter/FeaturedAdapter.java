package com.amstr.runscore.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amstr.runscore.R;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.featuredViewHolder> {
    ArrayList<FeaturedHelperClass> featuredLocations;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredLocations) {
        this.featuredLocations = featuredLocations;
    }

    @NonNull
    @Override
    public featuredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design,parent,false);
        featuredViewHolder featuredViewHolder = new featuredViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull featuredViewHolder holder, int position) {
        FeaturedHelperClass featuredHelperClass = featuredLocations.get(position);
        holder.icon.setImageResource(featuredHelperClass.getIcon());
        holder.value.setText(featuredHelperClass.getValue());
        holder.desc.setText(featuredHelperClass.getDesc());
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public static class featuredViewHolder extends RecyclerView.ViewHolder {

        // variables
        ImageView icon;
        TextView value,desc;

        public featuredViewHolder(@NonNull View itemView) {
            super(itemView);

            // get id
            icon = itemView.findViewById(R.id.featured_icon);
            value = itemView.findViewById(R.id.featured_value);
            desc = itemView.findViewById(R.id.featured_desc);
        }
    }
}
