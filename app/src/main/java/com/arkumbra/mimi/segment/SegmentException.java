package com.arkumbra.mimi.segment;

/**
 * Created by lukegardener on 2018/09/06.
 */

public class SegmentException extends Exception {

  private String error;

  public SegmentException(String error) {
    super(error);
  }

  public String getError() {
    return error;
  }
}
