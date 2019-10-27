package com.arkumbra.mimi.segment;

import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by lukegardener on 2018/09/06.
 */

public interface SegmentManager extends AdapterView.OnItemClickListener {


  /**
   * Create new segment with 'segmentMarkerMs' as start time.
   * If the most recently used segment already has a start time set, 'segmentMarkerMs'
   * will be used as that segment's end time.
   *
   * Updates text of button to indicate whether next action will start or end a segment
   *
   * @param segmentMarkerMs
   */
  void recordSegment(int segmentMarkerMs, Button segmentRecordButton);


  /**
   * @return true if user has selected an already-recorded segment, false otherwise
   */
  boolean isSegmentCurrentlySelected();


  /**
   *
   * @return currently-selected segment object if there is one, null otherwise
   */
  Segment getCurrentlySelectedSegment();

  void deleteSegment(int segmentId);
}
