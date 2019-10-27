package com.arkumbra.mimi.player;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by lukegardener on 2018/08/08.
 */
public class MediaPlayerUpdateListeners {

  // TODO FIXME these aren't /actually/ listeners... ... ...

  private final SeekBar seekbar;
  private final TextView timePassed;
  private final TextView timeRemaining;

  public MediaPlayerUpdateListeners(SeekBar seekbar, TextView timePassed, TextView timeRemaining) {
    this.seekbar = seekbar;
    this.timePassed = timePassed;
    this.timeRemaining = timeRemaining;
  }

  public SeekBar getSeekbar() {
    return seekbar;
  }

  public TextView getTimePassed() {
    return timePassed;
  }

  public TextView getTimeRemaining() {
    return timeRemaining;
  }
}
