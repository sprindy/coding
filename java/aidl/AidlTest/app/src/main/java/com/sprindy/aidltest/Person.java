package com.sprindy.aidltest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sprindy on 17-11-2.
 */

public class Person implements Parcelable{
    private String name;
    private int    age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(Parcel source) {
        this.name = source.readString();
        this.age  = source.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {

        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        @Override
        public Person[] newArray(int i) {
            return new Person[i];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
