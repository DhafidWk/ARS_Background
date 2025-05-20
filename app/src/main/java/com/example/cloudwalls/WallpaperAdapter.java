package com.example.cloudwalls.adapter;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cloudwalls.R;
import com.example.cloudwalls.model.Wallpaper;

import java.io.File;
import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder> {

    private final Context context;
    private final List<Wallpaper> wallpapers;

    public WallpaperAdapter(Context context, List<Wallpaper> wallpapers) {
        this.context = context;
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallpaper, parent, false);
        return new WallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, int position) {
        Wallpaper wallpaper = wallpapers.get(position);

        // Load image with Glide
        Glide.with(context)
                .load(getResourceId(wallpaper.getImageResource(), "drawable"))
                .centerCrop()
                .into(holder.ivWallpaper);

        holder.btnDownload.setOnClickListener(v -> {
            // Check for storage permission on Android 10+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                downloadImage(wallpaper);
            } else {
                Toast.makeText(context, "Storage permission required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadImage(Wallpaper wallpaper) {
        try {
            // Create download directory if it doesn't exist
            File directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "CloudWalls");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Use Android's Download Manager
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" +
                    wallpaper.getImageResource());

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(wallpaper.getImageName())
                    .setDescription("Downloading wallpaper")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            "CloudWalls/" + wallpaper.getImageName() + ".jpg");

            downloadManager.enqueue(request);
            Toast.makeText(context, "Downloading wallpaper...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private int getResourceId(String resName, String defType) {
        return context.getResources().getIdentifier(resName, defType, context.getPackageName());
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    static class WallpaperViewHolder extends RecyclerView.ViewHolder {
        ImageView ivWallpaper;
        Button btnDownload;

        public WallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWallpaper = itemView.findViewById(R.id.ivWallpaper);
            btnDownload = itemView.findViewById(R.id.btnDownload);
        }
    }
}