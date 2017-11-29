package com.sprindy.samples.common;

public class ButtonEvent {
  public long timeStamp;
  public int keycode;
  public boolean press;

  public ButtonEvent() {
  }

  public ButtonEvent(int code, boolean p, long ts) {
    keycode = code;
    press = p;
    timeStamp = ts;
  }
}
