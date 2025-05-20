package com.example.cloudwalls;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cloudwalls.adapter.UserPostAdapter;
import com.example.cloudwalls.model.UserPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrendingFragment extends Fragment {

    private static final int STORAGE_PERMISSION_CODE = 100;
    private ViewPager2 viewPager;
    private List<UserPost> userPosts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending, container, false);

        // Check for storage permission
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q &&
                ActivityCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }

        // Initialize user posts
        initializeUserPosts();

        // Setup ViewPager
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL); // Tambahkan baris ini untuk enable swipe vertikal
        UserPostAdapter adapter = new UserPostAdapter(requireContext(), userPosts);
        viewPager.setAdapter(adapter);

        // Uncomment this to make viewpager pages look like cards with spacing
        // viewPager.setClipToPadding(false);
        // viewPager.setClipChildren(false);
        // viewPager.setOffscreenPageLimit(1);
        // int pageMargin = getResources().getDimensionPixelOffset(R.dimen.page_margin);
        // int pageOffset = getResources().getDimensionPixelOffset(R.dimen.page_offset);
        // viewPager.setPageTransformer((page, position) -> {
        //     float offset = position * -(2 * pageOffset + pageMargin);
        //     if (position < 0) {
        //         page.setTranslationX(-offset);
        //     } else {
        //         page.setTranslationX(offset);
        //     }
        // });

        return view;
    }

    private void initializeUserPosts() {
        userPosts = new ArrayList<>();

        // Mr. Harry's post
        List<String> harryWallpapers = Arrays.asList("nature1", "nature2", "nature3");
        userPosts.add(new UserPost("Mr Harry", "img", harryWallpapers));

        // Mr. Agung's post
        List<String> agungWallpapers = Arrays.asList("winter1", "winter2", "winter3");
        userPosts.add(new UserPost("Mr Agung", "img1", agungWallpapers));

        // Ms. Lily's post
        List<String> lilyWallpapers = Arrays.asList("rasp1", "rasp2", "rasp3");
        userPosts.add(new UserPost("Ms Lily", "img2", lilyWallpapers));

        // Mr. David's post
        List<String> davidWallpapers = Arrays.asList("bw1", "bw2", "bw3");
        userPosts.add(new UserPost("Mr David", "img3", davidWallpapers));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE &&
                (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            // Permission granted
        }
    }
}