package com.example.a7rssfeed;

public class RSSFeed {
    private String title;
    private int image; // R.drawable.image_name returns an id as int
    private String URL;

    public RSSFeed(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
