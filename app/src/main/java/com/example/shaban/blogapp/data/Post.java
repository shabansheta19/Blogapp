package com.example.shaban.blogapp.data;

/**
 * Created by shaban on 6/18/2018.
 */

public class Post {
    private String img;
    private String title;
    private String description;
    private String timeStamp;
    private String userId;

    public Post() {
    }

    public Post(String img, String title, String description, String timeStamp) {
        this.img = img;
        this.title = title;
        this.description = description;
        this.timeStamp = timeStamp;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
