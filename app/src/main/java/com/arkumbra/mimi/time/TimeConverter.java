package com.arkumbra.mimi.time;

/**
 * Created by lukegardener on 2018/09/05.
 */

public class TimeConverter {
  private static final String UNDECLARED_TIME = "...";

  /**
   * Converts milliseconds into hh : mm : ss format
   * @param milliseconds
   * @return formatted string
   */
  public String getFormattedTime(long milliseconds) {
    int hours = (int) (milliseconds / (1000 * 60 * 60));
    int minutes = (int) ((milliseconds % (1000 * 60 * 60)) / (1000 * 60));
    int seconds = (int) (((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

    StringBuilder sb = new StringBuilder();

    sb.append(String.format("%02d", hours));
    sb.append(":");
    sb.append(String.format("%02d", minutes));
    sb.append(":");
    sb.append(String.format("%02d", seconds));

    return sb.toString();
  }

  public String getFormattedTimeHandlingNegative(long milliseconds) {
    if (milliseconds < 0) {
      return UNDECLARED_TIME;
    }

    return getFormattedTime(milliseconds);
  }
}
