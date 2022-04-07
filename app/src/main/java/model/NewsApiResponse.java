package model;

import java.io.Serializable;
import java.util.List;

import model.NewsHeaders;

public class NewsApiResponse implements Serializable {
    String status;
    int totalResults;
    List<NewsHeaders> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsHeaders> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsHeaders> articles) {
        this.articles = articles;
    }
}
