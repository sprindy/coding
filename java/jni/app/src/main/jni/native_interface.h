#ifndef __NATIVE_INTERFACE_H__
#define __NATIVE_INTERFACE_H__


#ifdef __cplusplus
extern "C" {
#endif
void Java_com_sprindy_samples_hello_MainActivity_onButtonDataUpdate
        (WVR_DeviceType deviceType,WVR_InputId keycode, bool press);

void Java_com_sprindy_samples_hello_MainActivity_onAnalogDataUpdate(WVR_DeviceType deviceType,float Axis_x, float Axis_y);
void Java_com_sprindy_samples_hello_MainActivity_onImuDataUpdate(WVR_DeviceType deviceType,float data[]);

#ifdef __cplusplus
}
#endif
#endif