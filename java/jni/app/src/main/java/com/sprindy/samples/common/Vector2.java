package com.sprindy.samples.common;

/**
 * Created by sprindy on 17-11-22.
 */

public final class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "; " + this.y + ")";
    }
}
