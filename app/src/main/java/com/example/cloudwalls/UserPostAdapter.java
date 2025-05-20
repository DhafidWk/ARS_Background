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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cloudwalls.R;
import com.example.cloudwalls.model.UserPost;

import java.io.File;
import java.util.List;

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostViewHolder> {

    private final Context context;
    private final List<UserPost> userPosts;

    public UserPostAdapter(Context context, List<UserPost> userPosts) {
        this.context = context;
        this.userPosts = userPosts;
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user_post, parent, false);
        return new UserPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        UserPost userPost = userPosts.get(position);

        // Set profile image
        int profileImageId = context.getResources().getIdentifier(
                userPost.getProfileImage(), "drawable", context.getPackageName());
        holder.ivProfilePic.setImageResource(profileImageId);

        // Set user name
        holder.tvProfileName.setText(userPost.getUserName());

        // Setup follow button
        holder.btnFollow.setOnClickListener(v -> {
            holder.btnFollow.setText("Following");
        });

        // Setup viewpager for wallpapers
        WallpaperPagerAdapter wallpaperAdapter = new WallpaperPagerAdapter(
                context, userPost.getWallpapers());
        holder.imageViewPager.setAdapter(wallpaperAdapter);

        // Download button click listener
        holder.btnDownload.setOnClickListener(v -> {
            // Get current wallpaper position
            int currentWallpaperPosition = holder.imageViewPager.getCurrentItem();
            String wallpaperName = userPost.getWallpapers().get(currentWallpaperPosition);

            // Check for storage permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
                    ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                downloadImage(wallpaperName);
            } else {
                Toast.makeText(context, "Storage permission required", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadImage(String wallpaperName) {
        try {
            // Create download directory if it doesn't exist
            File directory = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "CloudWalls");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Use Android's Download Manager
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/drawable/" + wallpaperName);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle(wallpaperName)
                    .setDescription("Downloading wallpaper")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            "CloudWalls/" + wallpaperName + ".jpg");

            downloadManager.enqueue(request);
            Toast.makeText(context, "Downloading wallpaper...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userPosts.size();
    }

    static class UserPostViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfilePic;
        TextView tvProfileName;
        Button btnFollow;
        ViewPager2 imageViewPager;
        Button btnDownload;

        public UserPostViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvProfileName = itemView.findViewById(R.id.tvProfileName);
            btnFollow = itemView.findViewById(R.id.btnFollow);
            imageViewPager = itemView.findViewById(R.id.imageViewPager);
            btnDownload = itemView.findViewById(R.id.btnDownload);
        }
    }
}