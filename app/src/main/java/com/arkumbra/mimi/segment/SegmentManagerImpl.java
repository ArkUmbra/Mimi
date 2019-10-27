package com.arkumbra.mimi.segment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.arkumbra.mimi.R;
import com.arkumbra.mimi.player.SegmentRecordAdapter;

/**
 * Created by lukegardener on 2018/08/31.
 */

public class SegmentManagerImpl implements SegmentManager {

  private final SegmentRecordAdapter segmentRecordAdapter;

  public SegmentManagerImpl(SegmentRecordAdapter segmentRecordAdapter) {
    this.segmentRecordAdapter = segmentRecordAdapter;
  }

  @Override
  public void recordSegment(int segmentMarkerMs, Button segmentRecordButton) {
    Segment currentSegment = segmentRecordAdapter.peekLast();

    if (currentSegment == null || currentSegment.isSegmentCompleted()) {
      startNewSegment(segmentMarkerMs);
      segmentRecordButton.setText(R.string.segment_end);

    } else if (isMinimumDistanceFromStartTime(currentSegment, segmentMarkerMs)) {
      updateEndTimeOfSegment(currentSegment, segmentMarkerMs);
      segmentRecordButton.setText(R.string.segment_start);

    } else {
      // Couldn't do anything, so just return without updating the dataset
      return;
    }

    segmentRecordAdapter.notifyDataSetChanged();
  }

  @Override
  public boolean isSegmentCurrentlySelected() {
    return segmentRecordAdapter.isSegmentCurrentlySelected();
  }

  @Override
  public Segment getCurrentlySelectedSegment() {
    return segmentRecordAdapter.getSelectedSegment();
  }

  @Override
  public void deleteSegment(int segmentId) {
    segmentRecordAdapter.delete(segmentId);
  }

  private void startNewSegment(int segmentMarkerMs) {
    Segment segment = new Segment(segmentMarkerMs);

    segmentRecordAdapter.add(segment);
  }

  private boolean isMinimumDistanceFromStartTime(Segment currentSegment, int segmentMarkerMs) {
    return (segmentMarkerMs > currentSegment.getStartTime());
  }

  private boolean updateEndTimeOfSegment(Segment currentSegment, int segmentMarkerMs) {
//    if (currentSegment.getStartTime() >= segmentMarkerMs) {
//      // Segments need to have a positive difference between start and end time
//      return false;
//    }

    currentSegment.recordEnd(segmentMarkerMs);
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    // TODO

    ListView listView = (ListView) parent;
    Segment clickedSegment = (Segment) listView.getItemAtPosition(position);

    boolean successfullySelected = segmentRecordAdapter.selectSegment(clickedSegment);

    // get segment that was clicked
    // can only click segment if start and end time are set
    // start playing from segment start
    // highlight row
    // if segment end time is reached, do... something (restart segment, etc)
  }

}
