package com.arkumbra.mimi.player;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arkumbra.mimi.R;
import com.arkumbra.mimi.segment.Segment;
import com.arkumbra.mimi.segment.SegmentException;
import com.arkumbra.mimi.time.TimeConverter;

import java.util.LinkedList;

/**
 * Created by lukegardener on 2018/09/05.
 */

public class SegmentRecordAdapter extends BaseAdapter {

  private final LinkedList<Segment> segments = new LinkedList<>();
  private final TimeConverter timeConverter = new TimeConverter();
  private final LayoutInflater inflater;

  private Segment selectedSegment;


  public SegmentRecordAdapter(LayoutInflater inflater) {
    this.inflater = inflater;
  }

  public boolean selectSegment(Segment segment) {
    if (! segment.isSegmentCompleted()) {
      return false;
    }

    if (segment.equals(selectedSegment)) {
      // deselect a segment if a user selects it again
      this.selectedSegment = null;
    } else {
      this.selectedSegment = segment;
    }

    notifyDataSetChanged();
    return true;
  }

  public boolean isSegmentCurrentlySelected() {
    return selectedSegment != null;
  }

  public Segment getSelectedSegment() {
    return selectedSegment;
  }

  public void delete(int segmentId) {
    segments.remove(segmentId);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return segments.size();
  }

  @Override
  public Object getItem(int position) {
    return segments.get(position);
  }

  @Override
  public long getItemId(int position) {
    return segments.get(position).hashCode();
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Segment segment = segments.get(position);
    String startTime = timeConverter.getFormattedTime(segment.getStartTime());
    String endTime = timeConverter.getFormattedTimeHandlingNegative(segment.getEndTime());

    convertView = inflater.inflate(R.layout.segment_row, parent, false);

    ((TextView)convertView.findViewById(R.id.tv_segment_start))
            .setText(startTime);
    ((TextView)convertView.findViewById(R.id.tv_segment_end))
            .setText(endTime);
    convertView.setTag(segment.getId());

    if (segment.equals(selectedSegment)) {
      convertView.setBackgroundColor(Color.LTGRAY);
    }

    return convertView;
  }

  public Segment peekLast() {
    return segments.peekLast();
  }

  public void add(Segment segment) {
    this.segments.add(segment);
  }
}
