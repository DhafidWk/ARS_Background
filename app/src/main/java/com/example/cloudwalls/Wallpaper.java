package com.example.cloudwalls.model;

public class Wallpaper {
    private String imageResource;
    private String imageName;

    public Wallpaper(String imageResource, String imageName) {
        this.imageResource = imageResource;
        this.imageName = imageName;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getImageName() {
        return imageName;
    }
}