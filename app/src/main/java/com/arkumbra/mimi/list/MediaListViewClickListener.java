package com.arkumbra.mimi.list;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.arkumbra.mimi.MediaSelectActivity;
import com.arkumbra.mimi.PlayerActivity;

import java.io.File;

/**
 * Created by lukegardener on 2018/08/23.
 */

public class MediaListViewClickListener implements ListView.OnItemClickListener {

  private final Context context;
  private final ListUpdater listUpdater;

  public MediaListViewClickListener(Context context, ListUpdater listUpdater) {
    this.context = context;
    this.listUpdater = listUpdater;
  }


  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    ListView listView = (ListView) parent;
    File file = (File) listView.getItemAtPosition(position);

    if (file.isDirectory()) {
      launchNewInstanceOfMediaSelectActivity(file);
    } else {
      // Launch the player only if the user selected an actual file
      launchPlayerActivity(file);
    }
  }

  private void launchNewInstanceOfMediaSelectActivity(File file) {
    Intent launchPlayerIntent = new Intent(context, MediaSelectActivity.class);
    launchPlayerIntent.putExtra(Intent.EXTRA_TEXT, file.getAbsolutePath());

    context.startActivity(launchPlayerIntent);
  }

  private void launchPlayerActivity(File file) {
    Intent launchPlayerIntent = new Intent(context, PlayerActivity.class);
    launchPlayerIntent.putExtra(Intent.EXTRA_TEXT, file.getAbsolutePath());
    launchPlayerIntent.putExtra(Intent.EXTRA_TITLE, file.getName());
    launchPlayerIntent.putExtra(Intent.EXTRA_PACKAGE_NAME, file.getParentFile().getName());

    context.startActivity(launchPlayerIntent);
  }

}
