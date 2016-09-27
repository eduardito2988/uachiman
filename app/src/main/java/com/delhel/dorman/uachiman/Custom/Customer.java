package com.delhel.dorman.uachiman.Custom;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 22/08/2016.
 */
public class Customer implements Parcelable {

    private int id;
    private String name = "";

    public Customer() {
    }

    public Customer(Parcel source) {
        id = source.readInt();
        name = source.readString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
            // TODO Auto-generated method stub
        }

    };

    @Override
    public String toString() {
        return this.name;
    }

}