package com.arkumbra.mimi.player;

/**
 * Created by lukegardener on 2018/08/31.
 */

public interface PlayerStateManager {

  void updatePlayerState(PlayerState playerState);

  void handlePlayPauseClick();

  void jumpForward();

  void jumpBackward();

  int getCurrentPlayPositionTime();

}
