package com.arkumbra.mimi.player;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.arkumbra.mimi.segment.Segment;
import com.arkumbra.mimi.segment.SegmentManager;
import com.arkumbra.mimi.time.TimeConverter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by lukegardener on 2018/08/08.
 */

public class MediaPlayerUpdateHandler implements Runnable {

  private static final String TAG = "MediaPlayerUpdateHandlr";

  private final TimeConverter timeConverter = new TimeConverter();
  private final Handler handler = new Handler();
  private final MediaPlayer mediaPlayer;
  private final MediaPlayerUpdateListeners listeners;
  private final SegmentManager segmentManager;

  private boolean running = true;

  public MediaPlayerUpdateHandler(MediaPlayer mediaPlayer, SegmentManager segmentManager, MediaPlayerUpdateListeners listeners) {
    this.mediaPlayer = mediaPlayer;
    this.segmentManager = segmentManager;
    this.listeners = listeners;
  }

  @Override
  public void run() {
    if (mediaPlayer == null || ! running)
      return;

    seekToCorrectPositionIfPlayingWithinSegment();
    updateSeekbarAndTextBoxLabels();

    handler.postDelayed(this, 100);
  }

  private void seekToCorrectPositionIfPlayingWithinSegment() {
    if (! segmentManager.isSegmentCurrentlySelected()) {
      // nothing to do
      return;
    }

    Segment currentlySelectedSegment = segmentManager.getCurrentlySelectedSegment();

    int currentTimeMs = mediaPlayer.getCurrentPosition();

    if (currentTimeMs < currentlySelectedSegment.getStartTime() ||
            currentTimeMs > currentlySelectedSegment.getEndTime()) {

      mediaPlayer.seekTo(currentlySelectedSegment.getStartTime());
    }
  }

  private void updateSeekbarAndTextBoxLabels() {
    int timeCompleted = mediaPlayer.getCurrentPosition();
    int maxDuration = mediaPlayer.getDuration();

    long timeRemaining = maxDuration - timeCompleted;

    listeners.getSeekbar().setMax(maxDuration / 1000);
    listeners.getSeekbar().setProgress(timeCompleted / 1000);

    String timeCompletedFormatted = timeConverter.getFormattedTime(timeCompleted);
    String timeRemainingFormatted = timeConverter.getFormattedTime(timeRemaining);
    listeners.getTimePassed().setText(timeCompletedFormatted);
    listeners.getTimeRemaining().setText(timeRemainingFormatted);
  }

  public synchronized void setRunning(boolean running) {
    this.running = running;
  }

  public synchronized boolean isRunning() {
    return running;
  }
}
