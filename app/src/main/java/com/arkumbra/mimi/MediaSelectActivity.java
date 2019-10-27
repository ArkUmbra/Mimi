package com.arkumbra.mimi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkumbra.mimi.list.ListUpdater;
import com.arkumbra.mimi.list.MediaListViewClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaSelectActivity extends AppCompatActivity implements ListUpdater {
    private static final String TAG = "MediaSelect";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String PODCAST_ADDICT_DIRECTORY =
            "/storage/A8D6-3EA5/Android/data/com.bambuna.podcastaddict/files/podcast/";
    private static final String ANDROID_PODCAST_DIRECTORY =
            "/storage/emulated/0/Podcasts";


    private ListView directoryTrackSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_select_listview);

        directoryTrackSelect = (ListView) findViewById(R.id.lv_media_directory_select);


        String displayDirectory = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        if (displayDirectory == null) {
            populateListViewWithDefaultDirectories();

        } else {
            // Means we've been launched from another Activity, so let's set up the 'Up' button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            updateListedDirectory(new File(displayDirectory));
        }
    }

    private void populateListViewWithDefaultDirectories() {
        File podcastAddictDirectory = new File(PODCAST_ADDICT_DIRECTORY);
        File androidPodcastDirectory = new File(ANDROID_PODCAST_DIRECTORY);

        updateListedDirectory(podcastAddictDirectory, androidPodcastDirectory);
    }

    @Override
    public void updateListedDirectory(File... directories) {
        requestDirectoryReadPermissions();

        List<File> filesOrDirsToDisplay = new ArrayList<>();

        for (File directory : directories) {
            for (File file : directory.listFiles()) {
                if ((!file.isHidden()) &&
                        (file.isDirectory() || isMediaFile(file))) {
                    filesOrDirsToDisplay.add(file);
                }
            }
        }


        getSupportActionBar().setTitle(createTitleFromListedDirectories(directories));

        directoryTrackSelect.setAdapter(createSimpleListAdapter(filesOrDirsToDisplay));
        directoryTrackSelect.setOnItemClickListener(new MediaListViewClickListener(this, this));
    }

    private String createTitleFromListedDirectories(File[] directories) {
        if (directories.length == 1) {
            return directories[0].getName();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(": ");

        for (File dir : directories) {
            sb.append(dir.getName());
            sb.append(" : ");
        }

        return sb.toString();
    }

    private void requestDirectoryReadPermissions() {
        int permission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission == PackageManager.PERMISSION_DENIED) {
            Log.d(TAG, "No permission, returning");
            Toast.makeText(this, "Need permissions", Toast.LENGTH_LONG).show();
        }

        requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private ArrayAdapter<File> createSimpleListAdapter(List<File> files) {
        return new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, files);
    }

    private boolean isMediaFile(File file) {
        String fileNameLower = file.getName().toLowerCase();

        if (fileNameLower.endsWith(".jpg") ||
                fileNameLower.endsWith(".png") ||
                fileNameLower.endsWith(".ico")) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
