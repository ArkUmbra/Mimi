package com.arkumbra.mimi.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arkumbra.mimi.R;
import com.arkumbra.mimi.segment.SegmentManager;

import java.io.IOException;

/**
 * Created by lukegardener on 2018/08/31.
 */

public class PlayerStateManagerImpl implements PlayerStateManager {
  private static final String TAG = "PlayerStateManImpl";

  private final Activity activityContext;
  private final Button playPauseButton;
  private final SegmentManager segmentManager;

  private MediaPlayer mediaPlayer;
  private MediaPlayerUpdateHandler mediaPlayerUpdateHandler;

  private boolean initialised;

  public PlayerStateManagerImpl(Activity activityContext,
                                SegmentManager segmentManager,
                                Button playPauseButton) {

    this.activityContext = activityContext;
    this.segmentManager = segmentManager;
    this.playPauseButton = playPauseButton;
  }

  public void updatePlayerState(PlayerState playerState) {

    switch (playerState) {
      case INITIALISED:
        initialise();
        break;
      case PLAYING:
        play();
        break;
      case PAUSED:
        pause();
        break;
      case STOPPED:
        stop();
        break;
    }

  }

  private void initialise() {
    if (initialised) {
      Log.d(TAG,"Media player already initialised");
      return;
    }

    mediaPlayer = new MediaPlayer();

    String dataSource = activityContext.getIntent().getStringExtra(Intent.EXTRA_TEXT);

    try {
      mediaPlayer.setDataSource(dataSource);
    } catch (IOException e) {
      e.printStackTrace();
      Log.d(TAG, e.getMessage());
      Log.e(TAG, "Error loading datasource", e);
    }

    try {
      mediaPlayer.prepare();
    } catch (IOException e) {
      e.printStackTrace();
      Log.e(TAG, "Failed to prepare media player", e);
    }

    Log.d(TAG, "Duration: " + mediaPlayer.getDuration());

    SeekBar seekbar = (SeekBar) activityContext.findViewById(R.id.seekBar_player);
    TextView timePassed = (TextView) activityContext.findViewById(R.id.tv_timePassed);
    TextView timeRemaining = (TextView) activityContext.findViewById(R.id.tv_timeRemaining);

    MediaPlayerUpdateListeners listeners = new MediaPlayerUpdateListeners(
            seekbar, timePassed, timeRemaining);
    mediaPlayerUpdateHandler = new MediaPlayerUpdateHandler(mediaPlayer, segmentManager, listeners);

    activityContext.runOnUiThread(mediaPlayerUpdateHandler);

    this.initialised = true;
  }

  private void play() {
    validatePlayerInitialised();

    playPauseButton.setText(R.string.player_pause);
//    mediaPlayerUpdateHandler.setRunning(true);
    mediaPlayer.start();
  }

  private void pause() {
    validatePlayerInitialised();

    playPauseButton.setText(R.string.player_play);
    mediaPlayer.pause();
  }

  private void stop() {
    validatePlayerInitialised();

    mediaPlayerUpdateHandler.setRunning(false);
    mediaPlayer.stop();
    mediaPlayer.reset();
    mediaPlayer.release();
    this.initialised = false;
  }


  private void validatePlayerInitialised() {
    if (! initialised) {
      throw new RuntimeException("Player needs to be initialised first");
    }
  }

  @Override
  public void handlePlayPauseClick() {
    if (mediaPlayer.isPlaying()) {
      pause();
    } else {
      play();
    }
  }

  @Override
  public void jumpForward() {
    int currentPosition = mediaPlayer.getCurrentPosition();

    mediaPlayer.seekTo(currentPosition + (10 * 1000));
  }

  @Override
  public void jumpBackward() {
    int currentPosition = mediaPlayer.getCurrentPosition();

    mediaPlayer.seekTo(currentPosition - (10 * 1000));
  }

  @Override
  public int getCurrentPlayPositionTime() {
    return mediaPlayer.getCurrentPosition();
  }


}
