package com.arkumbra.mimi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.arkumbra.mimi.player.PlayerState;
import com.arkumbra.mimi.player.PlayerStateManager;
import com.arkumbra.mimi.player.PlayerStateManagerImpl;
import com.arkumbra.mimi.player.SegmentRecordAdapter;
import com.arkumbra.mimi.segment.SegmentManager;
import com.arkumbra.mimi.segment.SegmentManagerImpl;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
  private static final String TAG = "PlayerActivity";

  private PlayerStateManager playerStateManager;

  private Button btnPlayPause;
  private ListView lvSegmentRecord;
  private SegmentManager segmentManager;

  // TODO maintain media player state during reorientation / resume / etc

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.player_constraint_layout);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    btnPlayPause = (Button) findViewById(R.id.btn_playpause);
    btnPlayPause.setOnClickListener(this);

    findViewById(R.id.btn_back10).setOnClickListener(this);
    findViewById(R.id.btn_forward10).setOnClickListener(this);
    findViewById(R.id.btn_segment).setOnClickListener(this);
    findViewById(R.id.btn_segment_delete).setOnClickListener(this);

    TextView title = (TextView) findViewById(R.id.tv_media_title);
    title.setText(getIntent().getStringExtra(Intent.EXTRA_TITLE));

    getSupportActionBar().setTitle(getIntent().getStringExtra(Intent.EXTRA_PACKAGE_NAME));

    createSegmentDisplayAndHandlers();
    playerStateManager = new PlayerStateManagerImpl(this, segmentManager, btnPlayPause);

//    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//      @Override
//      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        if (mediaPlayer != null && fromUser) {
//          seekBar.setProgress();
//        }
//
//      }
//
//      @Override
//      public void onStartTrackingTouch(SeekBar seekBar) {
//
//      }
//
//      @Override
//      public void onStopTrackingTouch(SeekBar seekBar) {
//
//      }
//    });


//        String dataSource = "/storage/A8D6-3EA5/Android/data/com.bambuna.podcastaddict/files/podcast/Podcast Beyond"
//                + "Podcast_Beyond_Episode_551_11037.mp3";
//    String dataSource = "/storage/emulated/0/Podcasts/276_10902.mp3";

  }

  private void createSegmentDisplayAndHandlers() {
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    SegmentRecordAdapter segmentRecordAdapter = new SegmentRecordAdapter(inflater);

    lvSegmentRecord = (ListView) findViewById(R.id.lv_segment_record);
    lvSegmentRecord.setAdapter(segmentRecordAdapter);

    segmentManager = new SegmentManagerImpl(segmentRecordAdapter);
    lvSegmentRecord.setOnItemClickListener(segmentManager);
  }

  @Override
  protected void onStart() {
    super.onStart();

    playerStateManager.updatePlayerState(PlayerState.INITIALISED);
  }

  @Override
  protected void onPause() {
    super.onPause();

    playerStateManager.updatePlayerState(PlayerState.PAUSED);
  }

  @Override
  protected void onStop() {
    super.onStop();

    playerStateManager.updatePlayerState(PlayerState.STOPPED);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_playpause:
        playerStateManager.handlePlayPauseClick();
        break;
      case R.id.btn_back10:
        playerStateManager.jumpBackward();
        break;
      case R.id.btn_forward10:
        playerStateManager.jumpForward();
        break;
      case R.id.btn_segment:
        recordOrEndSegment(v);
        break;
      case R.id.btn_segment_delete:
        deleteSegment(v);
    }

  }

  private void deleteSegment(View v) {
    int positionTag = (Integer) v.getTag();
    segmentManager.deleteSegment(positionTag);
  }

  private void recordOrEndSegment(View btnSegment) {
    int currentTimeMs = playerStateManager.getCurrentPlayPositionTime();

    segmentManager.recordSegment(currentTimeMs, (Button)btnSegment);
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

}
