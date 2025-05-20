package com.example.cloudwalls;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Get reference to the delete cache option
        LinearLayout deleteCacheContainer = findViewById(R.id.delete_cache_container);

        // Set click listener for delete cache option
        deleteCacheContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCache();
            }
        });
    }

    /**
     * Method to handle deleting the app's cache
     */
    private void deleteCache() {
        try {
            // Delete the cache directory
            File cacheDir = getCacheDir();
            deleteDir(cacheDir);

            // Show success message
            Toast.makeText(this, "Cache deleted successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Show error message
            Toast.makeText(this, "Failed to delete cache: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Recursively delete a directory and its contents
     * @param dir the directory to delete
     * @return true if deletion was successful
     */
    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}