package com.sprindy.samples.common;


public final class Quaternion {
    public float x;
    public float y;
    public float z;
    public float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public String toString() {
        return "(" + this.x + "; " + this.y + "; " + this.z + "; " + this.w + ")";
    }
}
