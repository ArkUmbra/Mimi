package com.arkumbra.mimi.segment;

/**
 * Created by lukegardener on 2018/08/31.
 */

public class Segment {
  private static int INCREMENTING_ID = 1;


  public static final int NOT_SET = -1;

  private int startTime = NOT_SET;
  private int endTime = NOT_SET;
  private int id;

  public Segment(int startTime) {
    this.startTime = startTime;

    this.id = INCREMENTING_ID;
    INCREMENTING_ID++;
  }

  public void recordEnd(int endTime) {
    this.endTime = endTime;
  }

  public boolean isSegmentCompleted() {
    return endTime > startTime;
  }

  public int getStartTime() {
    return startTime;
  }

  public int getEndTime() {
    return endTime;
  }

  public int getId() {
    return id;
  }
}
