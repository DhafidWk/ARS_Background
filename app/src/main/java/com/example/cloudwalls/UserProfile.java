package com.example.cloudwalls.model;

import java.util.List;

public class UserProfile {
    private String name;
    private String profileImage;
    private List<Wallpaper> wallpapers;

    public UserProfile(String name, String profileImage, List<Wallpaper> wallpapers) {
        this.name = name;
        this.profileImage = profileImage;
        this.wallpapers = wallpapers;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public List<Wallpaper> getWallpapers() {
        return wallpapers;
    }
}