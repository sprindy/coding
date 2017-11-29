package com.sprindy.samples.common;

import android.util.FloatMath;



public final class MathConverter {

    private MathConverter() {}

    private static final Quaternion identity = new Quaternion(0f, 0f, 0f, 1f);
    private static final Quaternion fPoseLeft = new Quaternion(-0.5f, 0.5f, -0.5f, 0.5f);
    private static final Quaternion fPoseRight = new Quaternion(-0.5f, -0.5f, 0.5f, 0.5f);

    private static final float q = (float) (1.0 / Math.sqrt(2.0));
    private static final Quaternion q1 = new Quaternion(0f, 0f, q, q);  //ber
    private static final Quaternion q2 = new Quaternion(0f, 0f, -q, q);
    public final static float EPS = 1e-6f;

    public static Quaternion mult(Quaternion q1, Quaternion q2) {
        float ww = (q1.z + q1.x) * (q2.x + q2.y);
        float yy = (q1.w - q1.y) * (q2.w + q2.z);
        float zz = (q1.w + q1.y) * (q2.w - q2.z);
        float xx = ww + yy + zz;
        float qq = 0.5f * (xx + (q1.z - q1.x) * (q2.x - q2.y));

        float w = qq - ww + (q1.z - q1.y) * (q2.y - q2.z);
        float x = qq - xx + (q1.x + q1.w) * (q2.x + q2.w);
        float y = qq - yy + (q1.w - q1.x) * (q2.y + q2.z);
        float z = qq - zz + (q1.z + q1.y) * (q2.w - q2.x);

        return new Quaternion(x, y, z, w);
    }

    public static Quaternion HmdToHTC(Quaternion q) {
        return mult(mult(q1, q), q2);
    }

    /**
     * Transform orientation from quaternion form to euler angles form.
     * @param q1
     * @return orientation in euler angles form
     */
    public static Vector3 toEuler(Quaternion q1) {
        // Algorithm from:
        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm

        float sqw = q1.w * q1.w;
        float sqx = q1.x * q1.x;
        float sqy = q1.y * q1.y;
        float sqz = q1.z * q1.z;
        float unit = sqx + sqy + sqz + sqw;

        float test = q1.x * q1.y + q1.z * q1.w;

        // Singularity at north pole.
        if (test > 0.499f * unit)
            return new Vector3((float)(2.0f * Math.atan2(q1.x, q1.w)), (float)Math.PI / 2.0f, 0.0f);

        // Singularity at south pole.
        if (test < -0.499f * unit)
            return new Vector3((float)(-2.0f * Math.atan2(q1.x, q1.w)), (float)-Math.PI / 2.0f, 0.0f);

        return new Vector3(
                (float) Math.atan2(2.0f * q1.y * q1.w - 2.0f * q1.x * q1.z, sqx - sqy - sqz + sqw),
                (float) Math.asin(2.0f * test / unit),
                (float) Math.atan2(2.0f * q1.x * q1.w - 2.0f * q1.y * q1.z, -sqx + sqy - sqz + sqw)
        );
    }

    public static Vector3 toEulerDegrees(Quaternion q1) {
    Vector3 anglesDeg = toEuler(q1);

    anglesDeg.x = (float)Math.toDegrees((double)anglesDeg.x);
    anglesDeg.y = (float)Math.toDegrees((double)anglesDeg.y);
    anglesDeg.z = (float)Math.toDegrees((double)anglesDeg.z);

    return anglesDeg;
    }

    /**
     * Returns inverse rotation quaternion around same axis.
     * @param source rotation quaternion
     * @return inverse to source rotation quaternion
     */
    public static boolean equalsZero(float value) {
        return java.lang.Math.abs(value) < EPS;
    }
    private static float lengthSquared(Quaternion q) {
        return q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w;
    }

    public static Quaternion conjugated(Quaternion q) {
        float lengthSq = lengthSquared(q);

        if (!equalsZero(lengthSq)) {
            lengthSq = 1.0f / lengthSq;
            return new Quaternion(-q.x * lengthSq, -q.y * lengthSq, -q.z * lengthSq, q.w * lengthSq);
        }

        return new Quaternion(0f, 0f, 0f, 0f);
    }

    public static float toAxisAngle(Quaternion q) {
        float outAngle;
        if ((q.w > 1.0f) || (q.w < -1.0f)) {
            outAngle = 0f;
            return outAngle;
        }

        outAngle = (float)(2.0f * java.lang.Math.acos((double)q.w));
        if (java.lang.Math.abs(outAngle) < EPS)
            outAngle = (float)(2.0f * java.lang.Math.asin(java.lang.Math.sqrt((double)q.x * q.x + q.y * q.y + q.z * q.z)));
        return Math.abs(normalizeAngle(outAngle));
    }
    public static float normalizeAngle(float angle) {
        while (angle < -java.lang.Math.PI)
            angle += 2 * java.lang.Math.PI;
        while (angle > java.lang.Math.PI)
            angle -= 2 * java.lang.Math.PI;
        return angle;
    }

    public static float radToDegree(float rad) {
        if (Float.isNaN(rad))
            return Float.NaN;

        return (float) (rad * 180 / java.lang.Math.PI);
    }

}
