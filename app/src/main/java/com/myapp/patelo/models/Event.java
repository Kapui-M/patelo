package com.myapp.patelo.models;

public class Event {
    private String title, description, imageUrl, date;

    public Event() {} // Needed for Firestore

    public Event(String title, String description, String imageUrl, String date) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getDate() { return date; }
}

