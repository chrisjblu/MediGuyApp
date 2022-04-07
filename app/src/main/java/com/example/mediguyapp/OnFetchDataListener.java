package com.example.mediguyapp;

import java.util.List;

import model.NewsHeaders;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeaders> list, String message);
    void onError(String message);
}
