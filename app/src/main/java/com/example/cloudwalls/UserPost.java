package com.example.cloudwalls.model;

import java.util.List;

public class UserPost {
    private String userName;
    private String profileImage;
    private List<String> wallpapers;

    public UserPost(String userName, String profileImage, List<String> wallpapers) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.wallpapers = wallpapers;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public List<String> getWallpapers() {
        return wallpapers;
    }
}