package com.example.mediguyapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder{
    TextView news_title, news_source;
    ImageView image_headline;
    CardView cardView;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        news_source = itemView.findViewById(R.id.news_source);
        news_title = itemView.findViewById(R.id.news_title);
        image_headline = itemView.findViewById(R.id.image_headline);
        cardView = itemView.findViewById(R.id.main_container);


    }
}
