package com.example.a7rssfeed;

import android.widget.ImageView;

import java.util.ArrayList;

public class Article {

    private String title;
    private String date;
    private String Author;
    private String description;




    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public static ArrayList<Article> generateArticles() {
        ArrayList<Article> articleList= new ArrayList<>();
        articleList.add(new Article());
        return articleList;
    }
}
