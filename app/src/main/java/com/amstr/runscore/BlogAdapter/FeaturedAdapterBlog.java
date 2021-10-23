package com.amstr.runscore.BlogAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amstr.runscore.R;

import java.util.ArrayList;

public class FeaturedAdapterBlog extends RecyclerView.Adapter<FeaturedAdapterBlog.featuredViewHolder> {
    ArrayList<BlogFeaturedHelperClass> featuredLocations;
    OnCardListener onCardListener;

    public FeaturedAdapterBlog(ArrayList<BlogFeaturedHelperClass> featuredLocations, OnCardListener onCardListener) {
        this.featuredLocations = featuredLocations;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public featuredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_blog_content_featured,parent,false);
        FeaturedAdapterBlog.featuredViewHolder featuredViewHolder = new FeaturedAdapterBlog.featuredViewHolder(view,onCardListener);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull featuredViewHolder holder, int position) {
        BlogFeaturedHelperClass blogFeaturedHelperClass = featuredLocations.get(position);
        holder.imgContent.setImageResource(blogFeaturedHelperClass.getImgContent());
        holder.author.setText(blogFeaturedHelperClass.getAuthor());
        holder.datePublish.setText(blogFeaturedHelperClass.getDatePublish());
        holder.title.setText(blogFeaturedHelperClass.getTitle());
        holder.content.setText(blogFeaturedHelperClass.getContent());
    }

    @Override
    public int getItemCount() {
        return featuredLocations.size();
    }

    public static class featuredViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // variables
        ImageView imgContent;
        TextView author,datePublish,title,content;
        OnCardListener onCardListener;

        public featuredViewHolder(@NonNull View itemView,OnCardListener onCardListener) {
            super(itemView);

            // get id
            imgContent = itemView.findViewById(R.id.img_content);
            author = itemView.findViewById(R.id.author);
            datePublish = itemView.findViewById(R.id.date_publish);
            title = itemView.findViewById(R.id.title_content);
            content = itemView.findViewById(R.id.content);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCardListener.cardOnClick(getAdapterPosition());
        }
    }

    public interface OnCardListener{
        void cardOnClick(int position);
    }
}
