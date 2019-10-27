package com.arkumbra.mimi.list;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by lukegardener on 2018/08/23.
 */

public class MediaListAdapter implements ListAdapter {

  private List<File> files;


  public ListAdapter initialise(List<File> files) {
    this.files = files;
    return this;
  }

  @Override
  public boolean areAllItemsEnabled() {
    return false;
  }

  @Override
  public boolean isEnabled(int position) {
    return false;
  }

  @Override
  public void registerDataSetObserver(DataSetObserver observer) {

  }

  @Override
  public void unregisterDataSetObserver(DataSetObserver observer) {

  }

  @Override
  public int getCount() {
    if (files == null) {
      return 0;
    }

    return files.size();
  }

  @Override
  public Object getItem(int position) {
    if (files == null) {
      return null;
    }

    return files.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return null;
  }

  @Override
  public int getItemViewType(int position) {
    return 0;
  }

  @Override
  public int getViewTypeCount() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return (files == null || files.isEmpty());
  }
}
