package com.sprindy.samples.common;

import android.graphics.PointF;

public class AnalogEvent {
  public static final byte UP = 0;
  public static final byte DOWN = 1;
  public static final byte MOVE = 2;

  public long timeStamp;
  public PointF mAxis = new PointF();
  public int analogId;
  public int type;
}
