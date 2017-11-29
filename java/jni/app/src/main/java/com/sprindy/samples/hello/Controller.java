package com.sprindy.samples.hello;

/**
 * Created by sprindy on 17-11-22.
 */

public class Controller {
    public interface Listener {
        void onButtonDataUpdate(int deviceType,int keycode, boolean press);
        void onAnalogDataUpdate(int deviceType,float Axis_x ,float Axis_y);
        void onImuDataUpdate(int deviceType,float[] data);
    }
}
