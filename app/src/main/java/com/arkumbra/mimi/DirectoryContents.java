package com.arkumbra.mimi;

import java.io.File;
import java.util.List;

/**
 * Created by lukegardener on 2018/08/24.
 */

public class DirectoryContents {

  private final List<File> directories;
  private final List<File> files;

  public DirectoryContents(List<File> directories, List<File> files) {
    this.directories = directories;
    this.files = files;
  }

  public List<File> getDirectories() {
    return directories;
  }

  public List<File> getFiles() {
    return files;
  }

}
