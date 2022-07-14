package com.example.colormixerapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ColorInfo implements Parcelable {
    private String color1,color2,color3,color4;
    private String result;
    private String name;

    public ColorInfo(){}

    public ColorInfo(String color1, String color2, String color3, String color4, String result, String name) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.result = result;
        this.name = name;
    }

    protected ColorInfo(Parcel in) {
        color1 = in.readString();
        color2 = in.readString();
        color3 = in.readString();
        color4 = in.readString();
        result = in.readString();
        name = in.readString();
    }

    public static final Creator<ColorInfo> CREATOR = new Creator<ColorInfo>() {
        @Override
        public ColorInfo createFromParcel(Parcel in) {
            return new ColorInfo(in);
        }

        @Override
        public ColorInfo[] newArray(int size) {
            return new ColorInfo[size];
        }
    };

    public ColorInfo(int color1, int color2, int color3, int color4,int result, String colorID) {
        this.color1 = String.valueOf(color1);
        this.color2 = String.valueOf(color2);
        this.color3 = String.valueOf(color3);
        this.color4 = String.valueOf(color4);
        this.result = String.valueOf(result);
        this.name = colorID;
    }

    public String getColor1() {
        return color1;
    }



    public String getColor2() {
        return color2;
    }



    public String getColor3() {
        return color3;
    }



    public String getColor4() {
        return color4;
    }



    public String getResult() {
        return result;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color1);
        dest.writeString(color2);
        dest.writeString(color3);
        dest.writeString(color4);
        dest.writeString(result);
        dest.writeString(name);
    }
}
