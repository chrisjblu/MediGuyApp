package com.example.mediguyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import model.NewsHeaders;

public class NewsDetailsActivity extends AppCompatActivity {
    NewsHeaders headers;
    TextView txt_title, txt_author, txt_time, txt_dets, txt_content;
    ImageView imgnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        txt_title = findViewById(R.id.news_detail_title);
        txt_author = findViewById(R.id.news_author);
        txt_time = findViewById(R.id.news_author_time);
        txt_dets = findViewById(R.id.news_dets);
        txt_content = findViewById(R.id.news_dets_content);
        imgnews = findViewById(R.id.image_detail_news);

        headers = (NewsHeaders) getIntent().getSerializableExtra("data");

        txt_title.setText(headers.getTitle());
        txt_author.setText(headers.getAuthor());
        txt_time.setText(headers.getPublishedAt());
        txt_dets.setText(headers.getDescription());
        txt_content.setText(headers.getContent());
        Picasso.get().load(headers.getUrlToImage()).into(imgnews);



    }
}