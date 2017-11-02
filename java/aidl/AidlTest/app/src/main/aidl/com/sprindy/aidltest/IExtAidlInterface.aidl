// IExtAidlInterface.aidl
package com.sprindy.aidltest;

import com.sprindy.aidltest.Person;

// Declare any non-default types here with import statements

interface IExtAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(byte aByte, int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Person> add(in Person person);
}

