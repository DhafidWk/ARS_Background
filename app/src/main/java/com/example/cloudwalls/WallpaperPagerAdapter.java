package com.example.cloudwalls.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloudwalls.R;

import java.util.List;

public class WallpaperPagerAdapter extends RecyclerView.Adapter<WallpaperPagerAdapter.WallpaperViewHolder> {

    private final Context context;
    private final List<String> wallpapers;

    public WallpaperPagerAdapter(Context context, List<String> wallpapers) {
        this.context = context;
        this.wallpapers = wallpapers;
        // Log untuk debugging
        System.out.println("WallpaperPagerAdapter created with " + wallpapers.size() + " wallpapers");
        for (int i = 0; i < wallpapers.size(); i++) {
            System.out.println("Wallpaper " + i + ": " + wallpapers.get(i));
        }
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallpaper_pager, parent, false);
        return new WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        String wallpaper = wallpapers.get(position);
        System.out.println("Binding wallpaper at position " + position + ": " + wallpaper);

        // Load image using resource ID
        int resourceId = context.getResources().getIdentifier(
                wallpaper, "drawable", context.getPackageName());

        if (resourceId != 0) {
            holder.ivWallpaper.setImageResource(resourceId);
            System.out.println("Resource ID found: " + resourceId);
        } else {
            System.err.println("WARNING: Resource not found for wallpaper: " + wallpaper);
            // Set a default image or error placeholder
            holder.ivWallpaper.setImageResource(R.drawable.img);
        }

        // Tambahkan onClick listener untuk memudahkan debugging
        holder.ivWallpaper.setOnClickListener(v -> {
            System.out.println("Wallpaper clicked: " + position + " - " + wallpaper);
        });
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    static class WallpaperViewHolder extends RecyclerView.ViewHolder {
        ImageView ivWallpaper;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWallpaper = itemView.findViewById(R.id.ivWallpaper);
        }
    }
}